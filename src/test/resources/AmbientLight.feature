Feature: Ambient Light

  The ambient light is used to turn on the low beam headlights when the ignition is off. This feature defines the
  constraints for which the ambient light is engaged involving the doors, exterior brightness and ignition lock.


  Background:
    Given ignition is on

    Rule: With activated ambient light, the low beam headlights are activated as soon as the engine is switched off
          and the ignition key is pulled out of the ignition lock.

    @requirement(ELS-17,ELS-20)
    Scenario: Ambient light is engaged
      Given ignition is off
      And the driver turned on ambient light
      Then the low beam headlights will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver engages ambient light with light rotary switch in auto
      Given the driver inserted the key without turning on ignition
      And the driver turned the light rotary switch to auto
      When the driver has turned ambient light on
      Then the low beam headlights will be deactivated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver opens the door with ambient light on
      Given the ambient light is on
      And ignition is off
      And 30 seconds have passed
      And the exterior brightness is above 200lx
      When the driver opens the door
      Then the low beam headlights will be deactivated

    @requirement(ELS-17)
    Scenario: Driver turns on ambient light with light rotary switch in auto
      Given the driver turned the light rotary switch to auto
      When the driver turns on ambient light
      Then the low beam headlights will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver removes ignition key with ambient light on
      Given the ambient light is on
      When the driver removes the ignition key
      And 30 seconds have passed
      Then the low beam headlights will be deactivated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver opens the door before 30 seconds pass of activation of ambient light
      Given the ambient light is on
      And ignition is off
      And 30 seconds have not passed
      When the driver opens the door
      Then the low beam headlights will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver removes ignition key before 30 seconds after activating ambient light
      Given the ambient light is on
      And 30 seconds have not passed
      When the driver removes the ignition key
      Then the low beam headlights will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver removes ignition key before 30 seconds pass of activation of ambient light
      Given the ambient light is on
      And 30 seconds have not passed
      When the driver removes the ignition key
      Then the low beam headlights will be activated

    @requirement(ELS-17,ELS-20)
    Scenario: Driver changes from daytime running light to ambient light without turning off ignition
      Given daytime running light is on
      When the driver has turned ambient light on
      And the driver turns off daytime running light
      Then the low beam headlights will be deactivated

    Rule: The low beam headlights activate when at least one door of the car opens and the exterior
    brightness is lower than 200lx. When all doors shut, the low beam headlights deactivate.

    @requirement(ELS-19)
    Scenario: Driver activated ambient light with opened door and exterior brightness below 200lx
      Given ignition is off
      And the driver opened the door
      And the driver turned on ambient light
      And the exterior brightness is below 200lx
      Then the low beam headlights will be activated

    @requirement(ELS-19)
    Scenario: Driver activated ambient light with closed door and exterior brightness below 200lx
      Given ignition is off
      And the driver closes the door
      And the driver turned on ambient light
      And the exterior brightness is below 200lx
      Then the low beam headlights will be deactivated

    Rule: Engaging darkness switch doesn't allow activation of ambient light

    @requirement(ELS-21)
    Scenario: Driver activated darkness switch
      Given ignition is off
      And the driver activated the darkness switch
      And the driver turned on ambient light
      Then the low beam headlights will be deactivated
