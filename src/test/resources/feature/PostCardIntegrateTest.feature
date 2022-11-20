Feature: POST Card Controller

# Scenario: 01 - Create card success completed data
# Scenario: 02 - Create card success with remove status true
# Scenario: 03 - Create card success in-completed data
# Scenario: 04 - Create card failed invalid status
# Scenario: 05 - Create card failed invalid priority value
# Scenario: 06 - Create card failed not found required field

Scenario: 01 - Create card success completed data
  When the client call "POST" path "/rest/card" with body
  """
  {
    "topic": "COMPLETED TOPIC",
    "content": "COMPLETED CONTENT",
    "status": "TODO",
    "priority": 0,
    "removeStatus": false
  }
  """
  Then the client receive status code of 201
  And the client receive result in "Object" format are as follows
  """
  {
    "id": "",
    "topic": "COMPLETED TOPIC",
    "content": "COMPLETED CONTENT",
    "status": "TODO",
    "priority": 0,
    "removeStatus": false,
    "createdTimestamp": "",
    "modifiedTimestamp": "",
    "completedTimestamp": null
  }
  """
  And server remove recently created or updated card

Scenario: 02 - Create card success with remove status true
  When the client call "POST" path "/rest/card" with body
  """
  {
    "topic": "REMOVE IS TRUE",
    "content": "REMOVE TRUE CONTENT",
    "status": "TODO",
    "priority": 2,
    "removeStatus": true
  }
  """
  Then the client receive status code of 201
  And the client receive result in "Object" format are as follows
  """
  {
    "id": "",
    "topic": "REMOVE IS TRUE",
    "content": "REMOVE TRUE CONTENT",
    "status": "TODO",
    "priority": 2,
    "removeStatus": false,
    "createdTimestamp": "",
    "modifiedTimestamp": "",
    "completedTimestamp": null
  }
  """
  And server remove recently created or updated card

Scenario: 03 - Create card success in-completed data
  When the client call "POST" path "/rest/card" with body
  """
  {
      "topic": "IN-COMPLETED TOPIC",
      "content": "IN-COMPLETED CONTENT",
      "priority": 0
  }
  """
  Then the client receive status code of 201
  And the client receive result in "Object" format are as follows
  """
  {
    "id": "",
    "topic": "IN-COMPLETED TOPIC",
    "content": "IN-COMPLETED CONTENT",
    "status": "TODO",
    "priority": 0,
    "removeStatus": false,
    "createdTimestamp": "",
    "modifiedTimestamp": "",
    "completedTimestamp": null
  }
  """
  And server remove recently created or updated card

Scenario: 04 - Create card failed invalid status
  When the client call "POST" path "/rest/card" with body
  """
  {
    "topic": "INVALID STATUS TOPIC",
    "content": "INVALID STATUS CONTENT",
    "status": "Invalid_Status",
    "priority": 0
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

Scenario: 05 - Create card failed invalid priority value
  When the client call "POST" path "/rest/card" with body
  """
  {
    "topic": "INVALID PRIORITY TOPIC",
    "content": "INVALID PRIORITY CONTENT",
    "status": "DONE",
    "priority": 99
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

Scenario: 06 - Create card failed not found required field
  When the client call "POST" path "/rest/card" with body
  """
  {
    "status": "DONE",
    "priority": 0
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
