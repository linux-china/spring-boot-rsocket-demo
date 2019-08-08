workflow "Project Build" {
  resolves = ["Setup Java Action"]
  on = "push"
}

action "Setup Java Action" {
  uses = "LucaFeger/action-maven-cli@765e218a50f02a12a7596dc9e7321fc385888a27"
  args = "-DskipTests package"
}
