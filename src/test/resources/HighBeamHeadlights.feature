Feature:

  The high beam headlights is interacted through the pitman arm. This feature defines the state of the headlight
  depending on the speed of the vehicle and the brake pedal.

  Background:
    Given the ignition is on

    Rule: As long as the pitman arm is pulled towards the driver, the high beam headlight is active

    @requirement(ELS-30)
    Scenario: Driver engages high beam headlight
      When the driver moves pitman arm towards the driver
      Then the high beam headlight will turn on