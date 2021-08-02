Feature: Low Beam Headlights

  The low beam headlights are interacted through a light rotary switch. This feature is for defining the state of the headlight
  depending on the ignition of the car and the brightness setting of the outside of the vehicle.

  Background:
    Given the ignition is on

  Rule: Low beam headlight can be activated when the ignition is on

    @requirement(ELS-14)
    Scenario: Driver engages light rotary switch on
      When the driver turns the light rotary switch to the on position
      Then the low beam headlight will be activated

    @requirement(ELS-14)
    Scenario: Driver turns light rotary switch off
      When the driver turns the light rotary switch to the off position
      Then the low beam headlight will be deactivated

    Rule: Low beam headlights have 50% brightness when ignition is off

    @requirement(ELS-15)
    Scenario: Driver turns light rotary switch on when ignition is off
      Given the ignition is off
      When the driver turns the light rotary switch on
      Then the low beam headlight is activated with 50% brightness

    @requirement(ELS-15)
    Scenario: Driver turns light rotary switch off when ignition is off
      Given the ignition is off
      When the driver turns the light rotary switch to the off position
      Then the low beam headlight will be deactivated

    Rule: Low Beam Headlights are off with ignition off and light rotary switch in auto

    @requirement(ELS-16)
    Scenario: Driver turns light rotary switch to auto with ignition off
      Given the ignition is off
      When the driver turns light rotary switch to auto
      Then the low beam headlight will be deactivated

    @requirement(ELS-16)
    Scenario: Driver turns light rotary switch from auto to on with ignition on
      Given the ignition is off
      And the driver turned light rotary switch to auto
      When the driver turns the ignition on
      And the driver turns the light rotary switch on
      Then the low beam headlight will be activated

    Rule: Daytime running light is started when engine is started

    @requirement(ELS-17)
    Scenario: Driver engages daytime running light
    When the driver turns daytime running light on
    Then the low beam headlights will be activated

    @requirement(ELS-17)
    Scenario: Driver engages daytime running light with ignition off
    Given the ignition is off
    When the driver turns daytime running light on
    And the driver has turned ambient light off
    Then the low beam headlight will be deactivated

    Rule: Ambient light is started when engine is turned off

    @requirement(ELS-17,ELS-20)
    Scenario: Driver engages ambient light
    Given the ignition is off
    When the driver turns on ambient light
    Then the low beam headlight will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver engages ambient light with light rotary switch in auto
      When the ignition is off
      And the driver has turned ambient light on
      And the driver turns light rotary switch to auto
      Then the low beam headlight will be deactivated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver opens the door with ambient light on
    Given the ambient light is on
    And the ignition is off
    And 30 seconds have passed since the ambient light was on
    And the exterior brightness is above 200lx
    When the driver opens the door
    Then the low beam headlight will be deactivated

      @requirement(ELS-17)
      Scenario: Driver turns on ambient light with light rotary switch in auto
      Given the ambient light is on
      And the driver turned light rotary switch to auto
      When the driver turns on ambient light
      Then the low beam headlight will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver removes ignition key with ambient light on
    Given the ambient light is on
    When the driver removes the ignition key
    And 30 seconds have passed since the ambient light was on
    Then the low beam headlight will be deactivated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver opens the door before 30 seconds pass of activation of ambient light
    Given the ambient light is on
    And the ignition is off
    And 30 seconds have not passed since the ambient light was activated
    When the driver opens the door
    Then the low beam headlight will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver removes ignition key before 30 seconds after activating ambient light
    Given the ambient light is on
    And 30 seconds have not passed since the ambient light was activated
    When the driver removes the ignition key
    Then the low beam headlight will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver removes ignition key before 30 seconds pass of activation of ambient light
    Given the ambient light is on
    And 30 seconds have not passed since the ambient light was activated
    When the driver removes the ignition key
    Then the low beam headlight will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver changes from daytime running light to ambient light
    Given daytime running light is on
    When the driver has turned ambient light on
    And the driver removes the ignition key
    And the driver immediately opens the door
    Then the low beam headlight will be activated

    Rule: The low beam headlights must remain active for at least 3 seconds

    @requirement(ELS-18)
    Scenario: Driver activates lower beam headlights with exterior brightness below 200lx
      When the driver turns light rotary switch to auto
      And the exterior brightness is below 200lx
      Then the low beam headlight will be activated
      And low beam headlight will be active at least 3 seconds

    @requirement(ELS-18)
    Scenario: Driver activates lower beam headlights with exterior brightness above 250lx
      When the driver turns light rotary switch to auto
      And the exterior brightness is above 250lx
      Then low beam headlight will be active at least 3 seconds
      But the low beam headlight will be deactivated

    @requirement(ELS-18)
    Scenario: Driver activates ambient lights with ignition off and exterior brightness below 200lx
    Given the ignition is off
    And the driver turned on ambient light
    And the exterior brightness is below 200lx
    When the driver turns light rotary switch to auto
    Then the low beam headlight will be deactivated

    Rule: Ignition must be turned off for activation of ambient light

    @requirement(ELS-19)
    Scenario: Driver opens the door with brightness below 200lx
      Given the ignition is off
      And the driver turned on ambient light
      When the driver opens the door
      And the exterior brightness is below 200lx
      Then the low beam headlight will be activated

    @requirement(ELS-19)
    Scenario: Driver closes the door with brightness below 200lx
      Given the ignition is off
      And the driver turned on ambient light
      And the driver opened the door
      And the exterior brightness is below 200lx
      When the driver closes the door
      Then the low beam headlight will be deactivated

    Rule: Engaging darkness switch doesn't allow activation of ambient light

    @requirement(ELS-21)
    Scenario: Driver activates ambient light after darkness switch
      Given the ignition is off
      When the driver engages the darkness switch
      And the driver turns on ambient light
      Then the low beam headlight will be deactivated

    Rule: When low or high beam headlights activate, tail lights are activated

    @requirement(ELS-22)
    Scenario: Driver engages daytime light
      When the driver turns daytime running light on
      Then the low beam headlight will be activated
      And the low beam tail light will be activated

    Rule: In USA or Canada, tail lights realise direction indicator lamps

    @requirement(ELS-23)
    Scenario: Driver turns light rotary switch on with USA car model
      Given the car is from the USA
      When the driver turns the light rotary switch on
      Then the back left direction indicator will be blinking
      And the back right direction indicator will be blinking

    @requirement(ELS-23)
    Scenario: Driver signals left while USA car model tail light is on
      Given the car is from the USA
      And the low beam headlight is on
      When the driver moves pitman arm downward
      Then the right indicator will not blink
      And the vehicle flashes all left indicators synchronously


    @requirement(ELS-24)
    Scenario: Driver signals left with light rotary switch on
      Given the driver turned the light rotary switch on
      And the left indicator is blinking
      When the vehicle drives slower than 10km per hour
      Then the left cornering light will turn on

    @requirement(ELS-24)
    Scenario: Driver signals right with light rotary switch on
      Given the driver turned the light rotary switch on
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
      When the driver engages the darkness switch
      Then the left cornering light will turn off

    @requirement(ELS-26)
    Scenario: Driver engages cornering light by turning wheel to right by 10 degrees
      Given the left indicator is not blinking
      And the right indicator is not blinking
      And the low beam headlight is on
      And the vehicle drives slower than 10km per hour
      When the driver turns the wheel to right within 10 degrees
      Then the right cornering light will turn on

    Rule: When reverse gear is activated, opposite cornering light of blinking indicator turns on

    @requirement(ELS-27)
    Scenario: Driver engages reverse gear while signalling left
    Given the left cornering light is off
    And the right cornering light is off
    And the driver moved pitman arm downward
    When the driver activates the reverse gear
    Then the right cornering light will turn on

    @requirement(ELS-27)
    Scenario: Driver engages reverse gear while signalling right
      Given the left cornering light is off
      And the right cornering light is off
      And the driver moved pitman arm upward
      When the driver activates the reverse gear
      Then the left cornering light will turn on

    Rule: When key is not inserted, the light rotary switch is on and the pitman arm is engaged then parking light is engaged

    @requirement(ELS-28)
    Scenario: Driver engages left parking light
    Given the ignition is off
    And the driver turned the light rotary switch on
    When the driver moves pitman arm downward
    Then the low beam headlight is on
    And the left tail light will be on
    And the brightness of the lights will be 10% of the normal brightness

    @requirement(ELS-28)
    Scenario: Driver engages right parking light
      Given the ignition is off
      And the driver turned the light rotary switch on
      When the driver moves pitman arm upward
      Then the left low beam headlight is on
      And the right tail light will be on
      And the brightness of the lights will be 10% of the normal brightness

    @requirement(ELS-28)
    Scenario: Driver engages left parking light with ambient light on
      Given the ignition is off
      And the ambient light is on
      And the driver turns the light rotary switch on
      And the exterior brightness is below 200lx
      And 30 seconds have passed since the ambient light was on
      When the driver moves pitman arm downward
      Then the left tail light will be on
      And the left low beam headlight is on

    @requirement(ELS-28)
    Scenario: Driver engages right parking light with ambient light on
      Given the ignition is off
      And the ambient light is on
      And the driver turns the light rotary switch on
      And the exterior brightness is below 200lx
      And 30 seconds have passed since the ambient light was on
      When the driver moves pitman arm upward
      Then the right tail light will be on
      And the right low beam headlight is on

    Rule: The normal brightness for all lights and indicators are 100%

    @requirement(ELS-29)
    Scenario: Driver engages light rotary switch on
      Given the Ignition is on
      When the driver turned the light rotary switch on
      Then the low beam headlight is activated with 100% brightness














