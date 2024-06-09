Feature: get rentals
    Scenario Outline:  get all rentals
        Given some rentals
        When the user requests all rentals
        Then all rents are returned
        
    Scenario Outline: get all rentals with empty list
        Given no rentals
        When the user requests all rentals
        Then an empty list is returned
