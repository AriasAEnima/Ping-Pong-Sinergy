class Registro extends React.Component {
  constructor(props) {
    super(props);
    this.state = {nick: "", email: "", password1: "", password2:"", seRegistro: ""};

        this.handleChangeNick = this.handleChangeNick.bind(this);
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.handleChangePassword1 = this.handleChangePassword1.bind(this);
        this.handleChangePassword2 = this.handleChangePassword2.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChangeNick(event) {
    this.setState({nick: event.target.value});
  }
   handleChangeEmail(event) {
    this.setState({email: event.target.value});
  }
   handleChangePassword1(event) {
    this.setState({password1: event.target.value});
  }
  handleChangePassword2(event) {
    this.setState({password2: event.target.value});
  }

  handleSubmit(event) {
    // alert('A nick was submitted: ' + this.state.nick + " y son  :" + (this.state.password1==this.state.password2) );
    if(this.state.password1!=this.state.password2){
        alert("Las contraseñas no coinciden");        
    }else if(this.state.nick=="" ||this.state.email=="" ||  this.state.password1==""){        
        alert("Hay campos vacios"); 
    }else{
        //this.setState({seRegistro:true}); 
        this.guardarUsuario();
    }
    event.preventDefault();
  }
  
 guardarUsuario(){
        const data = new FormData();
        data.append('nick', this.state.nick);
        data.append('email', this.state.email);
        data.append('password', this.state.password1);        
        fetch('/newuser', {
            method: 'POST',
            body: data
        })
                .then(response => response.json())
                .then(dataresponse => {            
                    if(dataresponse.nick!=""){
                       this.setState({seRegistro:  true});                         
                    }else{
                       this.setState({seRegistro:  false});   
                    }
                    
                });        
     
 }

  render() {
      if(this.state.seRegistro==""){
            return (
                <div>
                <form onSubmit={this.handleSubmit}>
                  <p>  
                     <label> Nick:  </label>                  
                    <input type="text" value={this.state.nick} onChange={this.handleChangeNick} />
                  </p>
                  <p>  
                     <label> Email:  </label>                  
                     <input type="text" id="emailinput" value={this.state.email} onChange={this.handleChangeEmail} />
                  </p>
                                   
                  <p>
                      <label> Password : </label>
                      <input type="password" name="password" value={this.state.password1} onChange={this.handleChangePassword1}/> 
                  </p>
                  
                  <p>
                      <label> Re-Type password : </label>
                      <input type="password" name="password" value={this.state.password2} onChange={this.handleChangePassword2}/> 
                  </p>
                  <p><input type="submit" value="Sign Up!" /></p>
                </form>
               </div>
            ); 
      }else if (this.state.seRegistro){
            return (
                <div>
                <h2> Registro Exitoso  {this.state.nick} ! </h2>
                 <form action="/login.html">
                    <input type="submit" value="Ir a login" />
                </form>
                
               </div>
            ); 
          
      }else{
           return (
                <div>
                <h2> Registro Fallido :(, puede que el nick ya exista  </h2>
                 <form action="/register.html">
                    <input type="submit" value="Volver al formulario" />
                </form>
                
               </div>
            ); 
          
      }
 
  }
}


ReactDOM.render(
        <Registro/>,
        document.getElementById('root')
        );