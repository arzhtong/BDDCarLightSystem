Feature: Low Beam Headlights

  The low beam headlights are interacted through a light rotary switch. This feature is for defining the state of the headlight
  depending on the ignition of the car and the brightness setting of the outside of the vehicle.

  Rule: Low beam headlight can be activated when the ignition is on

    @requirement(ELS-14)
    Scenario: Driver engages light rotary switch on
      Given the ignition is on
      When the driver turns the light rotary switch to the on position
      Then the low beam headlight will be activated

    @requirement(ELS-14)
    Scenario: Driver turns light rotary switch off
      Given the ignition is on
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
      When the driver turns the light rotary switch to auto
      Then the low beam headlight will be deactivated

    Scenario: Driver turns light rotary switch from auto to on with ignition on
      Given the ignition is off
      And light rotary switch is auto
      When the driver turns the ignition on
      And the driver turns the light rotary switch on
      Then the low beam headlight will be activated

    Rule: Daytime running light is started when engine is started

    @requirement(ELS-17)
    Scenario: Driver engages daytime running light
    Given the ignition is on
    When the driver turns daytime running light on
    Then the low beam headlights will be activated

    @requirement(ELS-17)
    Scenario: Driver engages daytime running light with ignition off
    Given the ignition is off
    When the driver turns daytime running light on
    Then the low beam headlights will not be activated

    Rule: Ambient light is started when engine is turned off

    @requirement(ELS-17,ELS-20)
    Scenario: Driver engages ambient light
    Given the ignition is on
    When the driver turns ambient light on
    And the ignition is off
    Then the low beam headlight will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver opens the door with ambient light on
    Given the ambient light is on
    And 30 seconds have passed since the ambient light was activated
    When the driver opens the door
    Then the low beam headlight will be deactivated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver removes ignition key with ambient light on
    Given the Ignition is on
    And 30 seconds have passed since the ambient light was activated
    When the driver removes the ignition key
    Then the low beam headlight will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver opens the door before 30 seconds pass of activation of ambient light
    Given the ambient light is on
    And 30 seconds have not passed since the ambient light was activated
    When the driver opens the door
    Then the low beam headlight will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver removes ignition key before 30 seconds pass of activation of ambient light
    Given the Ignition is on
    And 30 seconds have not passed since the ambient light was activated
    When the driver removes the ignition key
    Then the low beam headlight will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver removes ignition key before 30 seconds pass of activation of ambient light
    Given the Ignition is on
    And 30 seconds have not passed since the ambient light was activated
    When the driver removes the ignition key
    Then the low beam headlight will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver changes from daytime running light to ambient light
    Given the ignition is on
    And daytime running light is on
    When the driver turns on ambient light
    And the driver removes the ignition key
    And the driver immediately opens the door
    Then the low beam headlight will be activated










