Feature: Direction Blinking Indicator

  The pitman arm is used for controlling the direction blinking indicator. This feature is for defining positions available by the pitman arm for indicating through blinking.

    Background:
      Given ignition is on

     Rule: Direction blinking is only available when ignition is on.

    @requirement(ELS-1,ELS-5)
    Scenario: Driver signals for turning right
      When the driver moves pitman arm upward
      Then the vehicle flashes all right indicators synchronously

    @requirement(ELS-1,ELS-5)
    Scenario: Driver signals for turning left
      When the driver moves pitman arm downward
      Then the vehicle flashes all left indicators synchronously

   @requirement(ELS-1,ELS-5)
   Scenario: Driver turns on ignition when the pitman arm is up
     Given ignition is off
     And the driver moves pitman arm upward
     When the driver turns on ignition
     Then the vehicle flashes all right indicators synchronously

   Scenario: Driver changes signalling from turning left to right
     Given ignition is on
     And the vehicle flashes all left indicators synchronously
     When the driver moves pitman arm upward
     Then the vehicle flashes all right indicators synchronously

   @requirement(ELS-1,ELS-5)
   Scenario: Driver inserts key without turning on ignition with pitman arm up
     Given ignition is off
     And the driver moves pitman arm upward
     When the driver inserts the key without turning on ignition
     Then the right indicator will not blink
    Rule: Tip-Blinking is only available when ignition is on

    @requirement(ELS-2,ELS-5)
    Scenario: Driver engages tip-blinking on right side
      When the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held less than 0.5 seconds
      Then all right indicators flash for 3 flashing cycles

    @requirement(ELS-2,ELS-5)
    Scenario: Driver engages tip-blinking on left side
      When the driver moves pitman arm downward in tip-blinking position
      And the pitman arm is held less than 0.5 seconds
      Then all left indicators flash for 3 flashing cycles

    @requirement(ELS-1,ELS-2,ELS-5)
      Scenario: Driver changes tip-blinking on right side to turning left
      And the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held less than 0.5 seconds
      When the driver moves the pitman arm in the downward position
      Then the vehicle flashes all left indicators synchronously

    @requirement(ELS-2,ELS-5)
      Scenario: Driver stops ignition while tip-blinking on right
      Given the ignition is on
      And all right indicators flash for 3 flashing cycles
      When the driver turns the ignition off
      Then the vehicle will not have flashing cycles

    @requirement(ELS-2,ELS-5)
      Scenario: Driver tip-blinks on right and turns on ignition
      Given the ignition is off
      When the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held less than 0.5 seconds
      And the driver turns on the ignition
      Then the vehicle will not have flashing cycles

    Rule: Engage Pitman arm in another direction or engaging hazard switch will stop tip-blinking flashing cycle

    @requirement(ELS-3,ELS-5)
    Scenario: Driver changes tip-blinking from right to left
      And the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held less than 0.5 seconds
      When the driver moves pitman arm downward in tip-blinking position
      And the pitman arm is held for less than 0.5 seconds
      Then the right indicator tip-blinking will stop
      And all left indicators should flash for 3 flashing cycles

    @requirement(ELS-3,ELS-5)
    Scenario: Driver changes tip-blinking from right to direction blinking left
      And the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held less than 0.5 seconds
      When the driver moves pitman arm downward
      Then the right indicator tip-blinking will stop
      And the vehicle flashes all left indicators synchronously

    @requirement(ELS-3,ELS-5)
    Scenario: Driver engages hazard warning switch while tip-blinking right
      Given the ignition is on
      And the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held less than 0.5 seconds
      When the driver engages the hazard warning switch
      Then the right indicator will not tip-blink
      And the vehicle flashes all left indicators synchronously
      And the vehicle flashes all right indicators synchronously

    @requirement(ELS-3,ELS-5)
    Scenario: Driver turns off ignition and signals right
    Given the ignition is on
    And the driver moves pitman arm upward
    When the driver turns the ignition off
    And the driver moves pitman arm downward
    Then the left indicator tip-blinking will stop

    Rule: If the pitman arm is held for more than 0.5 seconds in a tip-blinking position then direction indicators cycles are released until pitman arm leaves the position



    @requirement(ELS-4,ELS-5)
    Scenario: Driver signals turning right with tip-blinking position
      When the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held for more than 0.5 seconds
      Then the vehicle flashes all right indicators synchronously
      But stops when pitman arm leaves tip-blinking position

    @requirement(ELS-4,ELS-5)
    Scenario: Driver tip-blinks left from signalling right with tip-blinking position
      Given the ignition is on
      And the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held for more than 0.5 seconds
      When the driver moves the pitman arm in the downward position
      And the pitman arm is held for less than 0.5 seconds
      Then all left indicators should flash for 3 flashing cycles



    Rule: Cars sold in the USA or Canada will have daytime running light dimmed by 50% during direction blinking

    @requirement(ELS-6)
    Scenario: Engage direction blinking by car sold in the USA
      When direction blinking is engaged by a car sold in the USA
      Then the daytime running light must be dimmed by 50%

    @requirement(ELS-6)
    Scenario: Engage direction blinking by car sold in the UK
      When direction blinking is engaged by a car sold in the UK
      Then the daytime running light will not be dimmed

    Rule: When pitman arm is engaged in the same direction, the 3 flashing cycles must first finish before new command is processed

    @requirement(ELS-7)
    Scenario: Engage the pitman arm in an upward direction blinking position during the 3 flashing cycles where the pitman arm was already put in an upward position
      Given ignition is on
      And the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held for less than 0.5 seconds
      When the driver moves pitman arm upward in tip-blinking position
      And the pitman arm is held for less than 0.5 seconds
      Then the 3 flashing cycles must finish before the right indicators start the direction blinking

    @requirement(ELS-7)
    Scenario: Engage the pitman arm in a downward tip-blinking position during the 3 flashing cycles where the pitman arm was already put in a downward position
      Given ignition is on
      And the driver moves pitman arm downward in tip-blinking position
      And the pitman arm is held for less than 0.5 seconds
      When the driver moves pitman arm downward in tip-blinking position
      And the pitman arm is held for less than 0.5 seconds downward position
      Then the 3 flashing cycles must finish before the left indicators start the new cycle of tip-blinking










