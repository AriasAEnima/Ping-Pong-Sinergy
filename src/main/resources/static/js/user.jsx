class User extends React.Component {
  constructor(props) {
    super(props);
    this.state = {nick: "", email: "",loading:true};
    
  }
  componentDidMount(){
       fetch('/getuser', {
            method: 'GET'
        }).then(response => response.json())
                .then(dataresponse => {                
                     this.setState({loading:false, nick:  dataresponse.nick, email: dataresponse.email });            
                    
                });             
  }
  render() {
      if(this.state.loading){         
            return (<div>
                      <h2> Cargando.... </h2>                 
                </div>);
          
      }else{
          
            return (<div>
                <h2> Nick : {this.state.nick} </h2>
                <h2> Email : {this.state.email} </h2>                       
                </div>);
      }
    
      
  }
  
}

ReactDOM.render(
        <User/>,
        document.getElementById('root')
        );