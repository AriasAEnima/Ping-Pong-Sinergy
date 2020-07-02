class WSClient extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,     
            msg: "",
            user: "",
            password: ""
        };     
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.sendLogin = this.sendLogin.bind(this)
    }


    componentDidMount() {
      
    }
    
     handleUsernameChange(event) {
        this.setState({ user: event.target.value });
      
    }

    handlePasswordChange(event) {
        this.setState({ password: event.target.value });
    }

    
    
    sendLogin() {   
         console.log(this.state.user);
        if(this.state.user === "" ){         
             this.setState({ msg: "Campos no validos" });
        } else{        
            const data = new FormData();       
            data.append('user', this.state.user);   
            data.append('password', this.state.password);          
            fetch('/login', {
                method: 'POST',
                body: data
            })
                    .then(response=> response.json())
                    .then(dataresponse=>{
                        console.log(dataresponse);                     
                        if(dataresponse.user!=null){
                            this.setState({ msg: "Ingreso Correctamente : "+dataresponse.user });
                        }else{
                             this.setState({ msg: "Contraseña/Usuario Incorrecto" });
                        }
                    
                        
                    });        
                }
     }
  
    render() {
        console.log("Rendering...");
        const {error, msg} = this.state;
        if (error) {
            return <div>Error: {error.message}</div>;        
        } else {
            return (
                    <div id="formularioLogin">      
                      <p>
                        <label htmlFor ="user">User : </label>
                        <input id="user" type="text" onChange={this.handleUsernameChange}/>
                        </p>
                         <p>
                          <label htmlFor ="password">Password  : </label>
                        <input id="password" type="text" onChange={this.handlePasswordChange}/>
                       
                        </p>
                         <button type="button" onClick={this.sendLogin}>Login ! </button>                   
                        <p>
                            {msg}
                        </p>
                    </div>
                    );
        }
    }
}


ReactDOM.render(
  <WSClient />,
  document.getElementById('container')
);