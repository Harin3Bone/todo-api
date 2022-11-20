Feature: GET Card Controller

# Scenario: 01 - Get all card in System
# Scenario: 02 - Get all card in Board
# Scenario: 03 - Get all card in Trash
# Scenario: 04 - Get card by ID success
# Scenario: 05 - Get card by ID failed not found
# Scenario: 06 - Get card by ID failed invalid ID

Scenario: 01 - Get all card in System
  When the client call "GET" path "/rest/card"
  Then the client receive status code of 200
  And the client receive result in "Array" format are as follows
  """
  12
  """

Scenario: 02 - Get all card in Board
  When the client call "GET" path "/rest/card/board"
  Then the client receive status code of 200
  And the client receive result in "Array" format are as follows
  """
  8
  """

Scenario: 03 - Get all card in Trash
  When the client call "GET" path "/rest/card/trash"
  Then the client receive status code of 200
  And the client receive result in "Array" format are as follows
  """
  4
  """

Scenario: 04 - Get card by ID success
  When the client call "GET" path "/rest/card/21459006-5f5e-48fa-a1e8-44b6e688d1af"
  Then the client receive status code of 200
  And the client receive result in "Object" format are as follows
  """
  {
    "createdTimestamp": "2022-10-28T19:28:19.000+00:00",
    "completedTimestamp": null,
    "topic": "GET Card A",
    "modifiedTimestamp": "2022-10-29T07:48:04.000+00:00",
    "id": "21459006-5f5e-48fa-a1e8-44b6e688d1af",
    "priority": 0,
    "removeStatus": false,
    "content": "GET Content",
    "status": "TODO"
  }
  """

Scenario: 05 - Get card by ID failed not found
  When the client call "GET" path "/rest/card/fd33b26c-00ee-4af2-9097-b7615593f009"
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Card not found for id: fd33b26c-00ee-4af2-9097-b7615593f009",
    "status": 400
  }
  """

Scenario: 06 - Get card by ID failed invalid ID
  When the client call "GET" path "/rest/card/123456"
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Invalid card id",
    "status": 400
  }
  """