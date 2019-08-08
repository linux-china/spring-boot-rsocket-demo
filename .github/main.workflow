workflow "New workflow" {
  on = "push"
  resolves = ["Setup Java Action"]
}

action "Setup Java Action" {
  uses = "actions/setup-java@v1"
  runs = "mvn"
  args = "-DskipTests package --file pom.xml"
}
