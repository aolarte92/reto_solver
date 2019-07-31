
import React from 'react';
import Form from './Forms/Containers/FormCreator'
import {submitButton,titleForm,formularioTest} from './Forms/Formularios';

var state = {
    
};


function changeHandler(variable, value) {
    state = {
        ...state,
        [variable]: value
    }
}

function submit(){
    console.log('submit', state)
    let data = new FormData()
    for ( var key in state ) {
        data.append(key, state[key]);
    }
    fetch( 'http://192.168.0.23:8080/api/reto', {
            method:'POST',
            body:data
        })
        .then(response => {
            if (response.status < 200 && response.status > 299) {
            }            
            console.log('response: ',response)
            return response.json();
        }).catch(function (error) {
            return { msg:error, code:600 };
        })
        .then(json => {
            return json;
        }).catch(function (error) {
            return { msg:error, code:700 };
        });
}

function App() {
    return (
        <div className="container">
            
                <Form  onChange={changeHandler} objectCreator=
                {
                    [
                        ...titleForm('Reto Solver'),
                        ...formularioTest(),                                      
                        ...submitButton("Enviar"),

                    ]
                } 
                    onSubmit={submit} ></Form>
            

        </div>
    );
}

export default App;
