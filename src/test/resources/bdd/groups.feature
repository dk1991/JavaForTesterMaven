Feature: Groups

  Scenario: Group creation
    Given a set of groups
    When I create a new group with name "xxx", header "yyy", footer "zzz"
    Then the new set of groups is equal to the old set with the edit group

  Scenario Outline: I want create 2 groups
    Given a set of groups
    When I create a new group with name "<name>", header "<header>", footer "<footer>"
    Then the new set of groups is equal to the old set with the edit group

    Examples:
      | name   | header | footer |
      | groupX | HeadX  | FootX  |
      | groupY | HeadY  | FootY  |