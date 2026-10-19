terraform {
  required_version = ">= 1.6.0"
}

variable "environment" {
  type        = string
  description = "Deployment environment name."
}

resource "terraform_data" "deployment_plan" {
  input = {
    environment = var.environment
    replicas    = 3
  }
}

output "deployment_plan" {
  value = terraform_data.deployment_plan.output
}
