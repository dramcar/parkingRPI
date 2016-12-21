import com.pi4j.io.gpio.*;


public class Display {

    private GpioController gpio;
    private GpioPinDigitalOutput ledA,ledB,ledC,ledD,ledE,ledF,ledG,none;

    Display (){
        // Crea controlador gpio
        gpio = GpioFactory.getInstance();
        
        // Pines de los leds para el display
        ledA = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, PinState.LOW);
        ledB = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, PinState.LOW);
        ledC = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, PinState.LOW);
        ledD = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, PinState.LOW);
        ledE = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, PinState.LOW);
        ledF = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, PinState.LOW);
        ledG = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.LOW);
        none = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, PinState.LOW);
    }


    void setNumero (int n) {
            GpioPinDigitalOutput[] alto;
            GpioPinDigitalOutput[] bajo;
        if (n == 0) {
            alto = new GpioPinDigitalOutput[]{ledA,ledB,ledC,ledD,ledE,ledF};
            bajo = new GpioPinDigitalOutput[]{ledG};
        }else if (n == 1) {
            alto = new GpioPinDigitalOutput[]{ledB,ledC};
            bajo = new GpioPinDigitalOutput[]{ledA,ledD,ledE,ledF,ledG};
        }else if (n == 2) {
            alto = new GpioPinDigitalOutput[]{ledA,ledB,ledD,ledE,ledG};
            bajo = new GpioPinDigitalOutput[]{ledF,ledC};
        }else if (n == 3) {
            alto = new GpioPinDigitalOutput[]{ledA,ledB,ledC,ledD,ledG};
            bajo = new GpioPinDigitalOutput[]{ledE,ledF};
        }else if (n == 4) {
            alto = new GpioPinDigitalOutput[]{ledB,ledC,ledF,ledG};
            bajo = new GpioPinDigitalOutput[]{ledA,ledD,ledE};
        }else if (n == 5) {
            alto = new GpioPinDigitalOutput[]{ledA,ledC,ledD,ledF,ledG};
            bajo = new GpioPinDigitalOutput[]{ledB,ledE};
        }else if (n == 6) {
            alto = new GpioPinDigitalOutput[]{ledA,ledC,ledD,ledE,ledF,ledG};
            bajo = new GpioPinDigitalOutput[]{ledB};
        }else if (n == 7) {
            alto = new GpioPinDigitalOutput[]{ledA,ledB,ledC};
            bajo = new GpioPinDigitalOutput[]{ledD,ledE,ledF,ledG};
        }else if (n == 8) {
            alto = new GpioPinDigitalOutput[]{ledA,ledB,ledC,ledD,ledE,ledF,ledG};
            bajo = new GpioPinDigitalOutput[]{none};
        }else if (n == 9) {
            alto = new GpioPinDigitalOutput[]{ledA,ledB,ledC,ledD,ledF,ledG};
            bajo = new GpioPinDigitalOutput[]{ledE};
        }else{
            alto = new GpioPinDigitalOutput[]{none};
            bajo = new GpioPinDigitalOutput[]{ledA,ledB,ledC,ledD,ledE,ledF,ledG};
        }

        gpio.setState(PinState.LOW, bajo);
        gpio.setState(PinState.HIGH,alto);
    }

}
