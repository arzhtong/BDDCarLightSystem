Feature: Low Beam Headlights

  The low beam headlights are interacted through a light rotary switch. This feature is for defining the state of the headlight
  depending on the ignition of the car and the brightness setting of the outside of the vehicle.

  Rule: Low beam headlight can be activated when the ignition is on

    Scenario: Driver engages light rotary switch on
      Given the ignition is on
      When the driver turns the light rotary switch to the on position
      Then the low beam headlight will be activated

    Scenario: Driver turns light rotary switch off
      Given the ignition is on
      When the driver turns the light rotary switch to the off position
      Then the low beam headlight will be deactivated


