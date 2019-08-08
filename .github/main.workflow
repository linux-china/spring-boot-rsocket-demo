workflow "New workflow" {
  on = "push"
  resolves = ["Setup Java Action"]
}

action "Setup Java Action" {
  uses = "actions/setup-java@232795a7c4c518061ce6a41f418b171de03cb907"
  runs = "mvn"
  args = "-DskipTests package --file pom.xml"
}
