Feature: Low Beam Headlights

  The low beam headlights are interacted through a light rotary switch. This feature is for defining the state of the headlight
  depending on the ignition of the car and the exterior brightness.

  Background:
    Given ignition is on

  Rule: Low beam headlights is activated by turning Light Rotary Switch on when ignition is on

    @requirement(ELS-14)
    Scenario Outline: Driver engages light rotary switch
      When the driver turns the light rotary switch <Light Rotary Switch Position>
      Then the low beam headlights will be <Low Beam Headlight State>

    Examples:

      | Light Rotary Switch Position | Low Beam Headlight State |  |
      | on                           | activated                |  |
      | off                          | deactivated              |  |




#    @requirement(ELS-14)
#    Scenario: Driver turns light rotary switch
#      When the driver turns the light rotary switch off
#      Then the low beam headlight will be deactivated

    Rule: Low beam headlights have 50% brightness when ignition is off with Light Rotary Switch on

    @requirement(ELS-15)
    Scenario: Driver turns light rotary switch on when ignition is off
      Given the ignition is off
      When the driver turns the light rotary switch on
      Then the low beam headlight is activated with 50% brightness

    @requirement(ELS-15)
    Scenario: Driver turns light rotary switch off when ignition is off
      Given the ignition is off
      When the driver turns the light rotary switch off
      Then the low beam headlights will be deactivated

    Rule: Low Beam Headlights are off with ignition off and light rotary switch in Auto

      @requirement(ELS-16)
      Scenario: Driver turns light rotary switch to auto with ignition on
        Given ignition is on
        When the driver turns light rotary switch to auto
        Then the low beam headlights will be deactivated

      @requirement(ELS-16)
    Scenario: Driver turns light rotary switch to auto with ignition off
      Given the ignition is off
      When the driver turns light rotary switch to auto
      Then the low beam headlights will be deactivated

  Rule: If the light rotary switch is in Auto, low beam headlights are only activated when exterior brightness is below
      200lx. Low beam headlights must remain active for at least 3 seconds.

    @requirement(ELS-18)
    Scenario: Driver activates low beam headlights with light rotary switch in Auto
      Given the driver turned light rotary switch to auto
      And the exterior brightness is below 200lx
      Then the low beam headlights will be activated
      And low beam headlight will be active at least 3 seconds

    @requirement(ELS-18)
    Scenario: Driver deactivates low beam headlights
      Given the driver turned light rotary switch to auto
      And the exterior brightness is above 250lx
      Then low beam headlight will be active at least 3 seconds
      But the low beam headlights will be deactivated



#    @requirement(ELS-18)
#    Scenario: Driver activated ambient lights with ignition off and exterior brightness below 200lx
#    Given the ignition is off
#    And the driver turned on ambient light
#    And the exterior brightness is below 200lx
#    And the driver turns light rotary switch to auto
#    Then the low beam headlights will be deactivated

    Rule: When low beam headlights activate, tail lights are activated

    @requirement(ELS-22)
    Scenario Outline: Driver engages low beam headlights
      Given the driver <Low Beam Headlight State> low beam headlights
      Then the tail light will be <Tail Light State>

#    Scenario: Driver disengages low beam headlights
#      Given the driver disengages low beam headlights
#      Then the tail light will be deactivated

    Examples:

      | Low Beam Headlight State | Tail Light State |  |
      | activated                | activated        |  |
      | deactivated              | deactivated      |  |



  Rule: In USA or Canada, tail lights realise direction indicator lamps. Signalling and hazard warning prioritize
    over normal tail lights.

    @requirement(ELS-23)
    Scenario: Light Rotary Switch is turned on with USA car model
      Given the car is from the USA
      And the driver turned the light rotary switch on
      Then the back left direction indicator will be blinking
      And the back right direction indicator will be blinking

    @requirement(ELS-23)
    Scenario: Driver signals left while USA car model tail light is on
      Given the car is from the USA
      And the low beam headlight is on
      When the driver moves pitman arm downward
      Then the right indicator will not blink
      And the vehicle flashes all left indicators synchronously



    Rule: The normal brightness for all lights and indicators are 100%

    @requirement(ELS-29)
    Scenario: Driver turned on low beam headlights of car
      Given the driver turned the light rotary switch on
      Then the low beam headlight is activated with 100% brightness














