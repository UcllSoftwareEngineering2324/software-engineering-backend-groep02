Feature:
    Scenario: given all cars when there are already cars  
        Given some cars
        When I want to get all cars 
        Then the data of all students is returned in json format