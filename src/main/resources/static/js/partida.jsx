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
        //console.log("In onMessage", evt);
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

    send(msg) {     
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
                   // console.log("On func call back ", msg);
                    var obj = JSON.parse(msg);   
                    this.limpie();
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
                    let message="";
                    if (p.keyIsDown(65)) {
                      message='{"jugador":"jugador1", "DIR":"LEFT" }';
                      wsreference.send(message);
                    }

                    if (p.keyIsDown(68)) {
                       message='{"jugador":"jugador1", "DIR":"RIGTH" }';
                       wsreference.send(message);
                    }

                    if (p.keyIsDown(87)) {
                       message='{"jugador":"jugador1", "DIR":"UP" }';
                       wsreference.send(message);
                    }

                    if (p.keyIsDown(83)) {
                       message='{"jugador":"jugador1", "DIR":"DOWN" }';
                       wsreference.send(message);
                    }
            };
        };
    }
    limpie(){
        this.myp5.clear();        
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