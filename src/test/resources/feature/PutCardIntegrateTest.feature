Feature: PUT Card Controller

# Scenario: 01 - Update card success completed data
# Scenario: 02 - Update card success only one field
# Scenario: 03 - Update card failed not found card
# Scenario: 04 - Update card failed invalid ID
# Scenario: 05 - Update card failed invalid data
# Scenario: 06 - Update card failed not found required field
# Scenario: 07 - Update card failed priority not in range
# Scenario: 08 - Restore card success
# Scenario: 09 - Restore failed not found Card
# Scenario: 10 - Restore failed invalid ID

Scenario: 01 - Update card success completed data
  When the client call "PUT" path "/rest/card/62ef0d73-370c-4559-a65a-d17c925a1675" with body
  """
  {
    "topic": "PUT Card A Update",
    "content": "PUT Content Update",
    "status": "IN_PROGRESS",
    "priority": 2,
    "removeStatus": false
  }
  """
  Then the client receive status code of 200
  And the client receive result in "Object" format are as follows
  """
  {
    "id": "62ef0d73-370c-4559-a65a-d17c925a1675",
    "topic": "PUT Card A Update",
    "content": "PUT Content Update",
    "status": "IN_PROGRESS",
    "priority": 2,
    "removeStatus": false,
    "createdTimestamp": "",
    "modifiedTimestamp": "",
    "completedTimestamp": null
  }
  """

Scenario: 02 - Update card success only one field
  When the client call "PUT" path "/rest/card/5ebad87d-da81-4880-89f4-79c3ee33bc83" with body
  """
  {
    "topic": "PUT Card B",
    "content": "PUT Content A Update",
    "status": "IN_PROGRESS",
    "priority": 2,
    "removeStatus": false
  }
  """
  Then the client receive status code of 200
  And the client receive result in "Object" format are as follows
  """
  {
    "id": "5ebad87d-da81-4880-89f4-79c3ee33bc83",
    "topic": "PUT Card B",
    "content": "PUT Content A Update",
    "status": "IN_PROGRESS",
    "priority": 2,
    "removeStatus": true,
    "createdTimestamp": "",
    "modifiedTimestamp": "",
    "completedTimestamp": null
  }
  """

Scenario: 03 - Update card failed not found card
  When the client call "PUT" path "/rest/card/918481a5-1959-4a06-9384-f8843144c6b0" with body
  """
  {
    "topic": "PUT Card D",
    "content": "PUT Content",
    "status": "DONE",
    "priority": 0,
    "removeStatus": false
  }
  """
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Card not found for id: 918481a5-1959-4a06-9384-f8843144c6b0",
    "status": 400
  }
  """

Scenario: 04 - Update card failed invalid ID
  When the client call "PUT" path "/rest/card/HELENA1234" with body
  """
  {
    "topic": "PUT Card E",
    "content": "PUT Content",
    "status": "TODO",
    "priority": 4,
    "removeStatus": false
  }
  """
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Invalid card id",
    "status": 400
  }
  """

Scenario: 05 - Update card failed invalid data
  When the client call "PUT" path "/rest/card/b7e1db33-e8b5-41cd-b6ac-f6835d76ef04" with body
  """
  {
    "topic": "PUT Card E",
    "content": "PUT Content",
    "status": "MOSTLY_IMPORTANT",
    "priority": 4,
    "removeStatus": false
  }
  """
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Invalid value of status",
    "status": 400
  }
  """

Scenario: 06 - Update card failed not found required field
  When the client call "PUT" path "/rest/card/62ef0d73-370c-4559-a65a-d17c925a1675" with body
  """
  {
    "status": "TODO",
    "priority": 0,
    "removeStatus": false
  }
  """
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "status": 400,
    "message": "Invalid data",
    "errorList": [
      {
        "field": "topic",
        "cause": "topic is required and cannot blank"
      },
      {
        "field": "content",
        "cause": "content is required"
      }
    ]
  }
  """

Scenario: 07 - Update card failed priority not in range
  When the client call "PUT" path "/rest/card/5ebad87d-da81-4880-89f4-79c3ee33bc83" with body
  """
  {
    "topic": "PUT Card G",
    "content": "PUT Content Again",
    "status": "TODO",
    "priority": 99,
    "removeStatus": false
  }
  """
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "status": 400,
    "message": "Invalid data",
    "errorList": [
      {
        "field": "priority",
        "cause": "priority must in range 0 - 5"
      }
    ]
  }
  """

Scenario: 08 - Restore card success
  When the client call "PUT" path "/rest/card/trash/5ebad87d-da81-4880-89f4-79c3ee33bc83"
  Then the client receive status code of 204

Scenario: 09 - Restore failed not found Card
  When the client call "PUT" path "/rest/card/trash/e3e6c92e-a60f-411a-a1e9-1d52f99b50d7"
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Card not found for id: e3e6c92e-a60f-411a-a1e9-1d52f99b50d7",
    "status": 400
  }
  """

Scenario: 10 - Restore failed invalid ID
  When the client call "PUT" path "/rest/card/trash/123456"
  Then the client receive status code of 400
  And the client receive result in "Object" format are as follows
  """
  {
    "errorList": [],
    "message": "Invalid card id",
    "status": 400
  }
  """