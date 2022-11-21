Feature: DELETE Card Controller

# Scenario: 01 - Soft Delete card success
# Scenario: 02 - Soft Delete card failed not found card
# Scenario: 03 - Soft Delete card failed invalid ID
# Scenario: 04 - Hard Delete card success
# Scenario: 05 - Hard Delete card failed not found card
# Scenario: 06 - Hard Delete card failed invalid ID
# Scenario: 06 - Hard Delete card failed not allow delete

Scenario: 01 - Soft Delete card success
  When the client call "DELETE" path "/rest/card/70a6a450-14cf-4f73-84ea-091343d500e2"
  Then the client receive status code of 204

Scenario: 02 - Soft Delete card failed not found card
  When the client call "DELETE" path "/rest/card/5b3b8b4e-b9f7-46ae-b50b-7717541dd02e"
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Card not found for id: 5b3b8b4e-b9f7-46ae-b50b-7717541dd02e",
    "status": 400
  }
  """

Scenario: 03 - Soft Delete card failed invalid ID
  When the client call "DELETE" path "/rest/card/123456"
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Invalid card id",
    "status": 400
  }
  """

Scenario: 04 - Hard Delete card success
  When the client call "DELETE" path "/rest/card/trash/8435d308-ffbc-419b-b8d3-45ffe56d032a"
  Then the client receive status code of 204

Scenario: 05 - Hard Delete card failed not found card
  When the client call "DELETE" path "/rest/card/281b1f46-981c-438a-b269-01d4511d7ac2"
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Card not found for id: 281b1f46-981c-438a-b269-01d4511d7ac2",
    "status": 400
  }
  """

Scenario: 06 - Hard Delete card failed invalid ID
  When the client call "DELETE" path "/rest/card/trash/123456"
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Invalid card id",
    "status": 400
  }
  """

Scenario: 06 - Hard Delete card failed not allow delete
  When the client call "DELETE" path "/rest/card/trash/06818097-5b84-4591-acb6-62b7d6623770"
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Invalid this card not in trash",
    "status": 400
  }
  """