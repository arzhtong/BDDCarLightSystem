Feature: Daytime Running Lights

  With an activated daytime running light, the low beam headlights are engaged when the engine is started.
  This feature defines the states of the ignition that allow the low beam headlight to be engaged.

  Background:
    Given ignition is on

    Rule: Daytime running light remains active as long as ignition key is in ignition lock

    @requirement(ELS-17)
    Scenario: Daytime running light engaged
      Given ignition is on
      And the driver turned daytime running light on
      Then the low beam headlights will be activated

    @requirement(ELS-17)
    Scenario: Daytime running light engaged with key inserted in ignition lock
      Given the driver inserted the key without turning on ignition
      And the driver turned daytime running light on
      And the driver turned the light rotary switch to auto
      Then the low beam headlights will be deactivated

    @requirement(ELS-17)
    Scenario: Daytime running light engaged with ignition off
      Given ignition is off
      And the driver turned daytime running light on
      Then the low beam headlights will be deactivated
