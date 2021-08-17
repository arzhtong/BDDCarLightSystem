Feature: Cornering Light

  If the low beam headlights are activated and direction blinking is requested, the cornering light is activated, when the
  vehicle drives slower than 10 km/h.

  Background:
    Given ignition is on

Rule: 5 seconds after passing the corner (the direction blinking is not active any more for 5 seconds),
the cornering light is switched off in a duration of 1 second.

@requirement(ELS-24)
Scenario: Driver engages left cornering light
Given the low beam headlight is on
And the left indicator is blinking
When the vehicle drives slower than 10km per hour
Then the left cornering light will turn on

@requirement(ELS-24)
Scenario: Driver engages right cornering light
Given the low beam headlight is on
And the right indicator is blinking
When the vehicle drives slower than 10km per hour
Then the right cornering light will turn on

@requirement(ELS-24)
Scenario: Vehicle passes left corner with cornering light
Given the left cornering light is on
When a duration of 5 seconds of passing the corner has occurred
Then the left cornering light will turn off within 1 second

@requirement(ELS-24)
Scenario: Vehicle passes right corner with cornering light
Given the right cornering light is on
When a duration of 5 seconds of passing the corner has occurred
Then the right cornering light will turn off within 1 second


Rule: When darkness switch is engaged, cornering light is deactivated
@requirement(ELS-25)
Scenario: Driver activates darkness switch with left cornering light on
Given the left cornering light is on
And the driver engaged the darkness switch
Then the left cornering light will turn off

Rule:

@requirement(ELS-26)
Scenario: Driver engages cornering light by turning wheel to right by 10 degrees
Given the low beam headlight is on
And the vehicle drives slower than 10km per hour
When the driver turns the wheel to right within 10 degrees
Then the right cornering light will turn on

Rule: When reverse gear is activated, opposite cornering light of blinking indicator turns on

@requirement(ELS-27)
Scenario: Driver engages reverse gear while signalling left
Given the driver moves pitman arm downward
And the driver activates the reverse gear
Then the right cornering light will turn on

@requirement(ELS-27)
Scenario: Driver engages reverse gear while signalling right
Given the driver moved pitman arm upward
And the driver activates the reverse gear
Then the left cornering light will turn on
