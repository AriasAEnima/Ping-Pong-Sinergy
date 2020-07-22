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
    constructor(sala,URL, callback) {
        this.URL = URL;
        this.wsocket = new WebSocket(URL);
        this.wsocket.onopen = (evt) => this.onOpen(evt);
        this.wsocket.onmessage = (evt) => this.onMessage(evt);
        this.wsocket.onerror = (evt) => this.onError(evt);
        this.receivef = callback;
        this.room=sala;
    }


    onOpen(evt) {
       console.log("In onOpen", evt);
       this.sendSala(this.room);
    }
    
    sendSala(r){
        this.wsocket.send('{"sala":'+r+'}');
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

function readCookie(name) {
       var nameEQ = name + "=";
       var ca = document.cookie.split(';');
       for(var i=0;i < ca.length;i++) {
           var c = ca[i];
           while (c.charAt(0)==' ') c = c.substring(1,c.length);
           if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
       }
       return null;
   }

class PartidaCanvas extends React.Component {
    constructor(props) {
        super(props);
         this.state = {loadingState: 'Loading Canvas ...', sala: readCookie("sala"), eq1:0, eq2:0, you: null, termino: false ,espectador: false};    
        this.comunicationWS = 
                new WSBBChannel(this.state.sala,PartidaServiceURL(),
                        (msg) => {
                   // console.log("On func call back ", msg);
                    var obj = JSON.parse(msg);                    
                    if( obj.pelota!=undefined){
                        this.limpie();                    
                        this.drawPelota(obj.pelota[0].x, obj.pelota[0].y);
                        this.drawJugador(obj.jugadores[0].x, obj.jugadores[0].y,1);
                        this.drawJugador(obj.jugadores[1].x, obj.jugadores[1].y,2);
                        this.actualizarPuntaje(obj.eq1,obj.eq2);                        
                    }else if(obj.you !=undefined){
                        this.setState({you:obj.you});    
                        console.log(obj.you);
                        console.log(this.state.sala + " <- esta es la sala");
                        this.myp5.setYou(obj.you);              
                    }else if(obj.termino!=undefined){
                        this.setState({termino:true});                                
                    }
                 
                    // this.drawPoint(20,20);
                });
                
        this.myp5 = null;       
        let wsreference = this.comunicationWS;       
        this.sketch = function (p) {  
             var you=null;
            p.setup = function () {
                p.createCanvas(1200, 700);
                p.background(100, 190, 201);
            };
             p.setYou= function(h){
                you=h;
            }

            p.draw = function () {
                    let message="";
                    if (p.keyIsDown(65)) {
                      message='{"jugador":"' + you+'", "DIR":"LEFT" }';
                      wsreference.send(message);
                    }

                    if (p.keyIsDown(68)) {
                       message='{"jugador":"' + you+'", "DIR":"RIGTH" }';
                       wsreference.send(message);
                    }

                    if (p.keyIsDown(87)) {
                       message='{"jugador":"' + you+'", "DIR":"UP" }';
                       wsreference.send(message);
                    }

                    if (p.keyIsDown(83)) {
                       message='{"jugador":"' + you+'", "DIR":"DOWN" }';
                       wsreference.send(message);
                    }
            };
        };
    }
    limpie(){        
        this.myp5.clear();        
        this.myp5.background(100, 190, 201);
    }
    
    drawPelota(x,y){
        this.myp5.fill(this.myp5.color(255,255,255));
        this.myp5.ellipse(x+50/2, y+50/2, 50, 50);
        
        //this.myp5.rect(x, y, 50, 50);        
    }
    
    drawJugador(x,y,n){
        if(n==1){
            
            this.myp5.fill(this.myp5.color(25, 111, 61));
        }else{
            this.myp5.fill(this.myp5.color(205, 92, 92));
        }
        
        this.myp5.rect(x, y, 50, 150);          
    }
    
    actualizarPuntaje(x,y){    
        this.setState({eq1: x, eq2: y});
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
        if(!this.state.termino){
        return(
                <div>
                    <h1> En la sala:  {this.state.sala}</h1>  
                    <p> Eres {this.state.you}</p>
                    <p>{this.state.eq1}  | {this.state.eq2} </p>  
                    
               
                </div>);
        }else{
            return(
            <div>
                <h1> En la sala:  {this.state.sala}</h1>  
                <p> Eres {this.state.you}</p>
                <p>{this.state.eq1}  | {this.state.eq2} </p>  
                
                <p> Ha terminado !! </p>
            </div>);
        }
    
    }
}

class Editor extends React.Component {
    render() {
        return (
                <div>
                      
                                
                    <PartidaCanvas/>
                    <div id="container"></div>                  
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