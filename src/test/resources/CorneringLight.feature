Feature: Cornering Light

 The cornering light feature is used for activating the cornering lights. This feature defines the states for which the
  corner lights are activated.

  Background:
    Given ignition is on

    Rule: 5 seconds after passing the corner, the cornering light is switched off in a duration of 1 second.

    @requirement(ELS-24)
    Scenario Outline: Driver engages cornering light
      Given the low beam headlight is on
      And the <Indicator Direction> indicator is blinking
      When the vehicle drives slower than 10km per hour
      Then the <Cornering Light Direction> cornering light will be on

    Examples:
      | Indicator Direction | Cornering Light Direction |
      | left                | left                      |
      | right               | right                     |

    @requirement(ELS-24)
    Scenario Outline: Vehicle passes corner with cornering light
      Given the <Indicator Direction> indicator is blinking
      When a duration of 5 seconds of passing the corner has occurred
      Then the <Cornering Light Direction> cornering light will turn off within 1 second

    Examples:
      | Indicator Direction | Cornering Light Direction |
      | left                | left                      |
      | right               | right                     |



    Rule: When darkness switch is engaged, cornering light is deactivated

    @requirement(ELS-25)
    Scenario Outline: Driver activates darkness switch with left cornering light on
      Given the <Indicator Direction> cornering light is <Cornering Light State>
      And the driver <Darkness Switch State> the darkness switch
      Then the <Cornering Light Direction> cornering light will be <New Cornering Light State>

    Examples:

      | Indicator Direction | Cornering Light State | Darkness Switch State | Cornering Light Direction | New Cornering Light State |
      | left                | on                    | activated             | left                      | off                       |
      | right               | on                    | activated             | right                     | off                       |
      | left                | on                    | activated             | right                     | off                       |
      | right               | on                    | activated             | left                      | off                       |
      | left                | off                   | deactivated           | left                      | off                       |
      | right               | off                   | deactivated           | right                     | off                       |
      | left                | on                    | deactivated           | left                      | on                        |
      | right                   | on                    | deactivated           | right                     | on                        |

    Rule: If driver is not signalling but the steering wheel is deflected by more than 10 degrees as well as
          the other constraints in ELS-24 are fulfilled, then the cornering light is activated.

    @requirement(ELS-26)
    Scenario: Driver engages cornering light by turning wheel to right by 10 degrees
      Given the low beam headlight is on
      And the vehicle drives slower than 10km per hour
      When the driver turns the wheel to right within 10 degrees
      Then the right cornering light will be on

    @requirement(ELS-26)
    Scenario: Driver engages cornering light by turning wheel to left by 10 degrees
      Given the low beam headlight is on
      And the vehicle drives slower than 10km per hour
      When the driver turns the wheel to left within 10 degrees
      Then the left cornering light will be on


    Rule: When reverse gear is activated, opposite cornering light of blinking indicator turns on

    @requirement(ELS-27)
    Scenario Outline: Driver engages reverse gear while signalling
      Given the driver moves pitman arm <Pitman Direction>
      And the driver activates the reverse gear
      Then the <Cornering Light Direction> cornering light will be on

    Examples:

    | Pitman Direction | Cornering Light Direction |
    | upward           | left                      |
    | downward         | right                     |

