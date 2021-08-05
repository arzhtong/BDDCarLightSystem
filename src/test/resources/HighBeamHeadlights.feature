Feature:

  The high beam headlights is interacted through the pitman arm. This feature defines the state of the headlight
  depending on the speed of the car and the brake pedal.

  Background:
    Given the ignition is on

    Rule: As long as the pitman arm is pulled towards the driver, the high beam headlight is active

    @requirement(ELS-30)
    Scenario: Driver engages high beam headlight
      When the driver moves pitman arm towards the driver
      Then the high beam headlight will turn on

    Rule: If the light rotary switch is on, pushing the pitman arm to the left will activate high beam headlights will a fixed illmination area

    @requirement(ELS-31)
    Scenario: Driver engages high beam headlight with fixed illumination area
      Given the driver turned the light rotary switch on
      When the driver moves pitman arm left
      Then the high beam headlight will turn on
      And the headlight will have a fixed area of 220m
      And the headlight will have 100% luminous strength

    Rule: When the light rotary switch is in auto, the high beam headlights are activated by moving the pitman arm to the back

    @requirement(ELS-32)
    Scenario: Driver engages high beam headlights with light rotary switch in auto
    Given the driver turned light rotary switch to auto
    When the driver moves pitman arm left
    Then the high beam headlight will turn on

    Rule: When adaptive high beam headlight is activated and car is driving faster than 30km/h with no advancing vehicle detected
          the street should be illuminated within 2 seconds.

    @requirement(ELS-33)
    Scenario: Driver engages high beam headlights while driving faster than 30km/h
    Given the vehicle drives faster than 30km per hour
    And no light of incoming vehicle is detected by camera
    When the driver moves pitman arm towards the driver
    Then the high beam headlight will turn on
    And the street should be illuminated in a manner based on vehicle speed within 2 seconds

    @requirement(ELS-33)
    Scenario: Driver engages high beam headlights while driving slower than 30km/h
      Given the vehicle drives slower than 30km per hour
      And no light of incoming vehicle is detected by camera
      When the driver moves pitman arm towards the driver
      Then the high beam headlight will turn on

    Rule: If the camera recognizes the lights of an advancing vehicle, an activated high beam headlight is reduced to low beam headlight within
    0.5 seconds

    @requirement(ELS-34)
    Scenario: Camera recognizes lights of advancing vehicle
    Given the high beam headlight is on
    When the camera recognizes lights of advancing vehicle
    Then the high beam headlight changes to low beam headlight within 0.5 seconds
    And the headlight will have an area of 65 metres
    And the headlight will have reduced luminous strength

  Rule: If no advancing vehicle is recognized any more, the high beam illumination is restored after 2 seconds

  @requirement(ELS-35)
  Scenario: No advancing vehicles are recognised anymore with low beam headlight on
  Given the low beam headlight is on
  And the camera recognizes lights of advancing vehicle
  When the camera stops recognising lights of anymore advancing vehicles
  Then the high beam headlight will turn on after 2 seconds


