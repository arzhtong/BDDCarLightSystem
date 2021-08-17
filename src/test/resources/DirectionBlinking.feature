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

#       @requirement(ELS-1,ELS-5)
#    Scenario: Driver signals for turning left
#      When the driver moves pitman arm downward
#      Then the vehicle flashes all left indicators synchronously

   @requirement(ELS-1,ELS-5)
   Scenario: Driver turns on ignition when the pitman arm is up
     Given ignition is off
     And the driver moved pitman arm upward
     When the driver turns on ignition
     Then the vehicle flashes all right indicators synchronously

  @requirement(ELS-1,ELS-5)
   Scenario: Driver changes signalling from turning left to right
     Given the driver moved pitman arm downward
     And the vehicle flashed all left indicators synchronously
     When the driver moves pitman arm upward
     Then the vehicle flashes all right indicators synchronously

   @requirement(ELS-1,ELS-5)
   Scenario: Driver inserts key without turning on ignition with pitman arm up
     Given ignition is off
     And the driver moved pitman arm upward
     When the driver inserts the key without turning on ignition
     Then the right indicator will not blink

    Rule: Tip-Blinking flashes for three cycles when pitman arm is held in tip-blinking position for less than 0.5 seconds

    @requirement(ELS-2,ELS-5)
    Scenario: Driver engages tip-blinking on right side
      When the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held for less than 0.5 seconds
      Then all right indicators flash for 3 flashing cycles

    @requirement(ELS-2,ELS-5)
    Scenario: Driver engages tip-blinking on left side
      When the driver moves pitman arm downward in tip-blinking position
      And the pitman arm is held for less than 0.5 seconds
      Then all left indicators flash for 3 flashing cycles

    @requirement(ELS-1,ELS-2,ELS-5)
      Scenario: Driver changes tip-blinking on right side to turning left
      Given the driver moved pitman arm upward in tip-blinking position
      And the pitman arm is held for less than 0.5 seconds
      When the driver moves the pitman arm in the downward position
      Then the vehicle flashes all left indicators synchronously

    @requirement(ELS-2,ELS-5)
      Scenario: Driver stops ignition while tip-blinking on right
      Given ignition is on
      And all right indicators are flashing for 3 flashing cycles
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
    Scenario: Driver changes tip-blinking from right to left
      Given the driver moves pitman arm upward in tip-blinking position for less than 0.5 seconds
      When the driver moves pitman arm downward in tip-blinking position for less than 0.5 seconds
      Then the right indicator tip-blinking will stop
      And all left indicators should flash for 3 flashing cycles

    @requirement(ELS-3,ELS-5)
    Scenario: Driver changes tip-blinking from right to signalling left
      Given the driver moves pitman arm upward in tip-blinking position for less than 0.5 seconds
      When the driver moves pitman arm downward
      Then the right indicator tip-blinking will stop
      And the vehicle flashes all left indicators synchronously

    @requirement(ELS-3,ELS-5)
    Scenario: Driver engages hazard warning switch while tip-blinking right
      Given the driver moves pitman arm upward in tip-blinking position for less than 0.5 seconds
      When the driver engages the hazard warning switch
      Then the vehicle flashes all left indicators synchronously
      And the vehicle flashes all right indicators synchronously


    Rule: If the pitman arm is held for more than 0.5 seconds in a tip-blinking position then direction indicators cycles are released until pitman arm leaves the position

    @requirement(ELS-4,ELS-5)
    Scenario: Driver signals turning right with tip-blinking position
      When the driver moves pitman arm upward in tip-blinking position for more than 0.5 seconds
      Then the vehicle flashes all right indicators synchronously
      But stops when pitman arm leaves tip-blinking position

    @requirement(ELS-4,ELS-5)
    Scenario: Driver tip-blinks left from signalling right in tip-blinking position
      Given the driver moves pitman arm upward in tip-blinking position for more than 0.5 seconds
      When the driver moves pitman arm downward in tip-blinking position for less than 0.5 seconds
      Then all left indicators should flash for 3 flashing cycles


    Rule: Cars sold in the USA or Canada will have daytime running light dimmed by 50% during signalling

    @requirement(ELS-6)
    Scenario: Driver signals right with car from USA
      When the driver moves pitman arm upward by a car from the USA
      Then the daytime running light must be dimmed by 50%

    @requirement(ELS-6)
    Scenario: Driver signals right with car from UK
      When the driver moves pitman arm upward by a car from the UK
      Then the daytime running light will not be dimmed

    Rule: When pitman arm is engaged in the same direction, the current cycle must finish before new command is started

    @requirement(ELS-7)
    Scenario: Engage the pitman arm in an upward direction blinking position during the 3 flashing cycles where the pitman arm was already put in an upward position
      Given the driver moves pitman arm upward in tip-blinking position for less than 0.5 seconds
      When the driver moves pitman arm upward
      Then the 3 flashing cycles must finish before the right indicators flash synchronously

    @requirement(ELS-7)
    Scenario: Engage the pitman arm in a downward tip-blinking position during the 3 flashing cycles where the pitman arm was already put in a downward position
      Given the driver moves pitman arm downward in tip-blinking position for less than 0.5 seconds
      When the driver moves pitman arm downward in tip-blinking position for less than 0.5 seconds
      Then the 3 flashing cycles must finish before the left indicators start the new cycle of tip-blinking










