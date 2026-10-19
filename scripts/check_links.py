from pathlib import Path
import re
import sys
from urllib.parse import unquote, urlsplit

ROOT = Path(__file__).resolve().parents[1]
LINK = re.compile(r'(?<!!)\[[^\]]*\]\(([^)\s]+)(?:\s+[^)]*)?\)')
HEADING = re.compile(r"^#{1,6}\s+(.+?)\s*#*\s*$", re.MULTILINE)


def anchor(text):
    text = re.sub(r"`([^`]*)`", r"\1", text).lower()
    text = re.sub(r"[^\w -]", "", text)
    return re.sub(r"\s+", "-", text.strip())


errors = []
for source in sorted(ROOT.rglob("*.md")):
    if any(part in {".git", "build", ".gradle"} for part in source.parts):
        continue
    content = source.read_text(encoding="utf-8")
    for match in LINK.finditer(content):
        raw = match.group(1).strip("<>")
        parsed = urlsplit(raw)
        if parsed.scheme or raw.startswith("//"):
            continue
        target_path = unquote(parsed.path)
        target = (source.parent / target_path).resolve() if target_path else source
        if target.is_dir():
            target = target / "README.md"
        if not target.is_file():
            errors.append(f"{source.relative_to(ROOT)}: missing {raw}")
            continue
        if parsed.fragment and target.suffix.lower() == ".md":
            target_content = target.read_text(encoding="utf-8")
            anchors = {anchor(title) for title in HEADING.findall(target_content)}
            if unquote(parsed.fragment) not in anchors:
                errors.append(f"{source.relative_to(ROOT)}: missing anchor {raw}")

if errors:
    print("\n".join(errors), file=sys.stderr)
    sys.exit(1)
print(f"Checked local Markdown links in {len(list(ROOT.rglob('*.md')))} files.")
