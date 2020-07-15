function PartidaServiceURL() {
    var chost=window.location.host;
    var nprotocol="";
    if(window.location.protocol=='http:'){
        nprotocol="ws";
    }else{
        nprotocol="wss";
    }
    console.log(window.location.protocol); 
    console.log(chost); 
    return nprotocol+'://'+chost+'/partidaService';
}

class WSBBChannel {
    constructor(URL, callback) {
        this.URL = URL;
        this.wsocket = new WebSocket(URL);
        this.wsocket.onopen = (evt) => this.onOpen(evt);
        this.wsocket.onmessage = (evt) => this.onMessage(evt);
        this.wsocket.onerror = (evt) => this.onError(evt);
        this.receivef = callback;
    }


    onOpen(evt) {
        console.log("In onOpen", evt);
    }
    onMessage(evt) {
        console.log("In onMessage", evt);
        // Este if permite que el primer mensaje del servidor no se tenga en cuenta.
        // El primer mensaje solo confirma que se estableció la conexión.
        // De ahí en adelante intercambiaremos solo puntos(x,y) con el servidor
        if (evt.data != "Connection established.") {
            this.receivef(evt.data);
        }
    }
    onError(evt) {
        console.error("In onError", evt);
    }

    send(x, y) {
        let msg = '{ "x": ' + (x) + ', "y": ' + (y) + "}";
        console.log("sending: ", msg);
        this.wsocket.send(msg);
    }


}


class PartidaCanvas extends React.Component {
    constructor(props) {
        super(props);
        this.comunicationWS =
                new WSBBChannel(PartidaServiceURL(),
                        (msg) => {
                    console.log("On func call back ", msg);
                    var obj = JSON.parse(msg);
                    console.log(obj.pelota.toString()+" Esto es parse");                
                    this.drawPelota(obj.pelota[0].x, obj.pelota[0].y);
                    this.drawJugador(obj.jugadores[0].x, obj.jugadores[0].y);
                    this.drawJugador(obj.jugadores[1].x, obj.jugadores[1].y);
                    // this.drawPoint(20,20);
                });
                
        this.myp5 = null;
        this.state = {loadingState: 'Loading Canvas ...'}
        let wsreference = this.comunicationWS;
        this.sketch = function (p) {

            p.setup = function () {
                p.createCanvas(700, 500);
            };


            p.draw = function () {
                if (p.mouseIsPressed === true) {
                    p.fill(0, 0, 0);
                    p.ellipse(p.mouseX, p.mouseY, 20, 20);
                    wsreference.send(p.mouseX, p.mouseY);
                }
                if (p.mouseIsPressed === false) {
                    p.fill(255, 255, 255);
                }
            };
        };
    }
    
    drawPelota(x,y){
        this.myp5.rect(x, y, 50, 50);        
    }
    
    drawJugador(x,y){
        this.myp5.rect(x, y, 50, 150);          
    }
    
    drawPoint(x, y) {
        this.myp5.ellipse(x, y, 20, 20);
    }

    componentDidMount() {
        this.myp5 = new p5(this.sketch, 'container');
        this.setState({loadingState: 'Canvas Loaded'});    
    }

    render()
    {
        return(
                <div>
                    <h4>Drawing status: {this.state.loadingState}</h4>
                </div>);
    }
}

class Editor extends React.Component {
    render() {
        return (
                <div>
                    <h1>Hello, {this.props.name}</h1>
                    <hr/>
                    <div id="toolstatus"></div>
                    <hr/>
                    <div id="container"></div>
                    <PartidaCanvas/>
                    <hr/>
                    <div id="info"></div>                    
                </div>
                );
    }
}

ReactDOM.render(
        <Editor name="Partida"/>,
        document.getElementById('root')
        );