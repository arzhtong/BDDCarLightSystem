Feature: Direction Blinking Indicator

  The pitman arm is used for controlling the direction blinking indicator. This feature is for defining positions available by the pitman arm for indicating through blinking
  at a synchronous rate.

    Background:
      Given ignition is on

    Rule: Direction blinking is only available when ignition is on

    @requirement(ELS-1,ELS-5)
    Scenario Outline: Driver signals for turning
      When the driver moves pitman arm <Pitman Direction>
      Then the vehicle flashes all <Indicating Direction> indicators synchronously

      Examples:
        | Pitman Direction | Indicating Direction |  |
        | upward           | right                |  |
        | downward         | left                 |  |


     @requirement(ELS-1,ELS-5)
     Scenario Outline: Driver turns on ignition when the pitman arm is in signalling position
       Given ignition is off
       And the driver moved pitman arm <Pitman Direction>
       When the driver turns on ignition
       Then the vehicle flashes all <Indicating Direction> indicators synchronously

       Examples:
         | Pitman Direction | Indicating Direction |  |
         | upward           | right                |  |
         | downward         | left                 |  |

     @requirement(ELS-1,ELS-5)
     Scenario Outline: Driver changes signalling direction
       Given the driver moved pitman arm <Pitman Direction>
       And the vehicle flashed all <Indicating Direction> indicators synchronously
       When the driver moves pitman arm <New Pitman Direction>
       Then the vehicle flashes all <New Indicating Direction> indicators synchronously

      Examples:

        | Pitman Direction | Indicating Direction | New Pitman Direction | New Indicating Direction |
        | upward           | right                | downward             | left                     |
        | downward         | left                 | upward               | right                    |


     @requirement(ELS-1,ELS-5)
     Scenario: Driver inserts key without turning on ignition with pitman arm up
       Given ignition is off
       And the driver moved pitman arm upward
       When the driver inserts the key without turning on ignition
       Then the right indicator will not blink

    Rule: Tip-Blinking flashes for three cycles when pitman arm is held in tip-blinking position for less than 0.5 seconds

    @requirement(ELS-2,ELS-5)
    Scenario: Driver engages tip-blinking on the left
      When the driver moves pitman arm downward in tip-blinking position for less than 0.5 seconds
      Then all left indicators should flash for 3 flashing cycles

    @requirement(ELS-2,ELS-5)
    Scenario: Driver engages tip-blinking on the right
      When the driver moves pitman arm upward in tip-blinking position for less than 0.5 seconds
      Then all right indicators should flash for 3 flashing cycles

    @requirement(ELS-1,ELS-2,ELS-5)
    Scenario Outline: Driver changes tip-blinking from one side to turning on another side
      Given the driver moved pitman arm <Initial Pitman Direction> in tip-blinking position
      And the pitman arm is held for less than 0.5 seconds
      When the driver moves pitman arm <New Pitman Direction>
      Then the vehicle flashes all <Indicating Direction> indicators synchronously

      Examples:
        | Initial Pitman Direction | New Pitman Direction | Indicating Direction |  |
        | upward                   | downward             | left                 |  |
        | downward                 | upward               | right                    |  |



    @requirement(ELS-2,ELS-5)
    Scenario: Driver stops ignition while tip-blinking on right
      Given ignition is on
      And all right indicators are flashing for 3 cycles
      When the driver turns the ignition off
      Then the vehicle will not have flashing cycles

   @requirement(ELS-2,ELS-5)
    Scenario: Driver turns on ignition while in tip-blinking position
      Given ignition is off
      And the driver moves pitman arm upward in tip-blinking position for less than 0.5 seconds
      When the driver turns on ignition
      Then the vehicle will not have flashing cycles

    Rule: Interruption request of tip-blinking flashing cycle will stop the current cycle and the new requested cycle starts

    @requirement(ELS-3,ELS-5)
    Scenario Outline: Driver changes tip-blinking side
      Given the driver moved pitman arm <Initial Pitman Direction> in tip-blinking position
      When the driver moves pitman arm <New Pitman Direction> in tip-blinking position for less than 0.5 seconds
      Then the <Initial Flashing Indicator> indicator tip-blinking will stop
      And all <New Flashing Indicator> indicators should flash for 3 flashing cycles

      Examples:

        | Initial Pitman Direction | New Pitman Direction | Initial Flashing Indicator | New Flashing Indicator |
        | upward                   | downward             | right                      | left                   |
        | downward                 | upward               | left                       | right                  |

    @requirement(ELS-3,ELS-5)
    Scenario Outline: Driver changes tip-blinking to signalling on opposite side
      Given the driver moved pitman arm <Initial Pitman Direction> in tip-blinking position for less than 0.5 seconds
      When the driver moves pitman arm <New Pitman Direction>
      Then the <Initial Flashing Indicator> indicator tip-blinking will stop
      And the vehicle flashes all <New Flashing Indicator> indicators synchronously

      Examples:

        | Initial Pitman Direction | New Pitman Direction | Initial Flashing Indicator | New Flashing Indicator |
        | upward                   | downward             | right                      | left                   |
        | downward                 | upward               | left                       | right                  |


    @requirement(ELS-3,ELS-5)
    Scenario: Driver engages hazard warning switch while tip-blinking right
      Given the driver moves pitman arm upward in tip-blinking position for less than 0.5 seconds
      When the driver engages the hazard warning switch
      Then the vehicle flashes all left indicators synchronously
      And the vehicle flashes all right indicators synchronously


    Rule: If the pitman arm is held for more than 0.5 seconds in a tip-blinking position then direction indicators cycles are released until pitman arm leaves the position

    @requirement(ELS-4,ELS-5)
    Scenario: Driver signals turning with tip-blinking position
      When the driver moves pitman arm upward in tip-blinking position for more than 0.5 seconds
      Then the vehicle flashes all right indicators synchronously

    @requirement(ELS-4,ELS-5)
    Scenario: Driver signals turning with tip-blinking position
      When the driver moves pitman arm downward in tip-blinking position for more than 0.5 seconds
      Then the vehicle flashes all left indicators synchronously

    @requirement(ELS-4,ELS-5)
      Scenario: Driver stops signalling right with tip-blinking position
      Given the driver moved pitman arm upward in tip-blinking position for more than 0.5 seconds
      When the driver moves pitman arm in neutral
      Then right indicators will stop flashing when pitman arm leaves tip-blinking position
#    @requirement(ELS-4,ELS-5)
#    Scenario: Driver tip-blinks left from signalling right in tip-blinking position
#      Given the driver moves pitman arm upward in tip-blinking position for more than 0.5 seconds
#      When the driver moves pitman arm downward in tip-blinking position for less than 0.5 seconds
#      Then all left indicators should flash for 3 flashing cycles
#


    Rule: Cars sold in the USA or Canada will have daytime running light dimmed by 50% during signalling

    @requirement(ELS-6)
    Scenario Outline: Driver signals with car
      Given the vehicle flashes all <Indicator Direction> indicators synchronously by a car from <Country of Sold Car>
      Then the daytime running light must be dimmed by <Dimmed Light Percentage>

      Examples:

        | Indicator Direction | Country of Sold Car | Dimmed Light Percentage |  |
        | right               | USA                 | 50%                     |  |
        | right               | Canada              | 50%                     |  |
        | left                | USA                 | 50%                     |  |
        | left                | Canada              | 50%                     |  |
        | right               | UK                  | 0%                      |  |
        | left                | UK                  | 0%                      |  |







