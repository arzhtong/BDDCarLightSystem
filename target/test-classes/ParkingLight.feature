Feature: Parking Light

  The parking light is the low beam and the tail lamp on the left or right side of the vehicle to illuminate the vehicle
  if it is parked on a dark road at night. To save battery charge, the parking light is activated with only 10% brightness of the
  normal low beam lamp and tail lamp.

  Background:
  Given ignition is on

  Rule: When key is not inserted, the light rotary switch is on and the pitman arm is engaged then parking light is engaged

  @requirement(ELS-28)
  Scenario Outline: Driver activated parking light
    Given ignition is <Ignition Key>
    And the driver turned the light rotary switch <Light Switch>
    And the driver moved pitman arm <Pitman Arm>
    Then the parking light is <Parking Light>

  Examples:
    | Ignition Key | Light Switch | Pitman Arm | Parking Light |
    | on           | on           | downward   | inactive      |
    | off          | on           | upward     | active        |
    | off          | off          | upward     | inactive      |
    | off          | on           | downward   | active        |
    | on           | off          | neutral    | inactive             |


