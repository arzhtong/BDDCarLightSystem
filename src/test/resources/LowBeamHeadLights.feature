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
