import React from 'react'

import './../styles.css'

import InputText from '../Components/InputTextContainer'
import Select from '../Components/SelectContainer'
import TextArea from '../Components/TextAreaContainer';
import Question from '../Components/QuestionContainer';
import Label from '../Components/LabelContainer';
import Calendar from '../Components/CalendarContainer';
import Checkbox from '../Components/CheckboxContainer';
import Title from '../Components/TitleContainer';
import ButtonSubmit from '../Components/ButtonSubmitContainer';
import ButtonClick from '../Components/ButtonClickContainer';
import PrintDocument from '../Components/PrintDocumentContainer';
import File from '../Components/FileContainer';






export const formTypes = {
    text: 'text',
    email: 'email',
    number: 'number',
    select: 'select',
    textarea: 'textarea',
    question: 'question',
    calendar: 'calendar',
    label: 'label',
    title: 'title',
    checkbox: 'checkbox',
    submit: 'submit',
    click: 'click',
    print: 'print',
    divContainer: 'divContainer',
    file: 'file',
}
const BLOCK_MSG = "Campo requerido"
const BLOCK_MSG_EMAIL = "Correo electrónico no valido"
const BLOCK_MSG_LENGTH = "El campo supera la longitud máxima permitida de "
var flag = false
var temp
var EMAIL_REGEX = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/

class FormCreator extends React.Component {


    constructor(props) {
        super(props)
        this.state = {
            object: this.props.objectCreator
        }
    }

    handlerChange = (dato, value, type = null) => {
        if(type !== null && type === formTypes.number){
            this.setState({ [dato]: parseInt(value) })
            this.props.onChange(dato, parseInt(value))
        }else{
            this.setState({ [dato]: value })
            this.props.onChange(dato, value)
        }
    }
    handlerChangeVisible = (idDiv, visible, idQuestion, check) => {
        let object = this.state.object.map(item => {
            if (item.variable !== undefined && item.variable === idQuestion) {
                return { ...item, checked: check }
            }
            if (item.variable !== undefined && item.variable === idDiv) {
                return { ...item, visible: visible }
            }
            return { ...item }
        })
        this.setState({ object })
    }

    handlerChangeCheck = (dato, value) => {
        let object = this.state.object.map(item => {
            if (item.variable !== undefined && item.variable === dato) {
                return { ...item, checked: value }
            }
            return { ...item }
        })
        this.setState({ object }, () => {
        })
    }

    handlerDadChange = (dad, value) => {
        temp = JSON.parse(JSON.stringify(this.state.object))
        this.dadChange(dad, value)
    }

    dadChange = (dad, value) => {
        for (let i = 0; i < temp.length; i++) {
            if (temp[i].type === formTypes.divContainer) {
                if (temp[i].visible) {
                    for (let j = 0; j < temp[i].items.length; j++) {
                        if (temp[i].items[j].variableDad === dad) {
                            for (let k = 0; k < temp[i].items[j].data.length; k++) {
                                if (temp[i].items[j].data[k].dad == value) {
                                    temp[i].items[j].data[k].visible = true
                                } else {
                                    temp[i].items[j].data[k].visible = false
                                }
                                this.handlerChange(temp[i].items[j].variable, null)
                                this.dadChange(temp[i].items[j].variable, null)
                            }
                        }
                    }
                }
            } else {
                for (let j = 0; j < temp[j].length; j++) {
                    if (temp[j].variableDad === dad) {
                        for (let k = 0; k < temp[j].data.length; k++) {
                            if (temp[j].data[k].dad == value) {
                                temp[j].data[k].visible = true
                            } else {
                                temp[j].data[k].visible = false
                            }
                            this.dadChange(temp[j].variable, null)
                        }
                    }
                }
            }
        }
        this.setState({ object: temp })
    }


    submitHandler = () => {
        flag = false
        let object = this.state.object.map(item => {
            return this.submitHandlerRecursivo(item)
        })
        this.setState({ object })
        if (!flag) {
            this.props.onSubmit()
        }
    }
    submitHandlerRecursivo = (item) => {
        if (item.type === formTypes.divContainer) {
            if (item.visible) {
                let objectItems = item.items.map(its => {
                    return this.submitHandlerRecursivo(its)
                })
                return { ...item, items: objectItems }
            }
            return { ...item }
        } else
            return this.validateForm(item)
    }

    validateForm = (item) => {
       
        if (item.required) {
            if (!(this.state[item.variable] !== undefined && this.state[item.variable] !== null)) {
                flag = true
                return { ...item, block: true, blockMsg:BLOCK_MSG}
            }
            else {
               return this.validateFormString(item)
            }
        } else {
            return this.validateFormString(item)
        }
    }
    validateFormString = (item) =>{
        if ((this.state[item.variable] !== undefined && this.state[item.variable] !== null)) {
            if (typeof this.state[item.variable] === 'string') {
                if (this.state[item.variable].trim() === "") {
                    flag = true
                    return { ...item, block: true, blockMsg:BLOCK_MSG }
                }else{
                    if(this.validateFormTypeEmail(item)){
                        flag = true
                        return { ...item, block: true, blockMsg:BLOCK_MSG_EMAIL }
                    }
                   
                }
            } 
            if(this.validateFormLength(item)){
                flag = true
                return { ...item, block: true, blockMsg:`${BLOCK_MSG_LENGTH} ${item.maxLength}` }
            }
        }
        return { ...item, block: false }
    }
    
    validateFormTypeEmail = (item) =>{
        if(item.type === formTypes.email){
            return !EMAIL_REGEX.test(String(this.state[item.variable]).toLowerCase())
            
        }
        return false
    }
    validateFormLength = (item) =>{
        switch (item.type) {
            case formTypes.text: return (this.state[item.variable].length > ((item.maxLength !== undefined && item.maxLength !== null)? item.maxLength: 255))                
            case formTypes.email:  return (this.state[item.variable].length > ((item.maxLength !== undefined && item.maxLength !== null)? item.maxLength: 100))
            case formTypes.number:  return (this.state[item.variable] > ((item.maxLength !== undefined && item.maxLength !== null)? item.maxLength: 999999999999))  
            case formTypes.textarea:  return (this.state[item.variable].length > ((item.maxLength !== undefined && item.maxLength !== null)? item.maxLength: 3000))  
            default: return false
        }
    }
        

    createForm = (item, pos) => {
        switch (item.type) {
            case formTypes.text: 
            case formTypes.email: 
            case formTypes.number: return <InputText config={item} onChange={this.handlerChange} key={`form-item-${pos}`} />
            case formTypes.select: return <Select config={item} onChange={this.handlerChange} onHandlerDadChange={this.handlerDadChange} key={`form-item-${pos}`} />
            case formTypes.textarea: return <TextArea config={item} onChange={this.handlerChange} key={`form-item-${pos}`} />
            case formTypes.question: return <Question config={item} onChange={this.handlerChange} onChangeVisible={this.handlerChangeVisible} key={`form-item-${pos}`} />
            case formTypes.calendar: return <Calendar config={item} onChange={this.handlerChange} key={`form-item-${pos}`} />
            case formTypes.label: return <Label config={item} onChange={this.handlerChange} key={`form-item-${pos}`} />
            case formTypes.title: return <Title config={item} onChange={this.handlerChange} key={`form-item-${pos}`} />
            case formTypes.checkbox: return <Checkbox config={item} onChange={this.handlerChange} key={`form-item-${pos}`} />
            case formTypes.submit: return <ButtonSubmit config={item} onSubmit={this.submitHandler} key={`form-item-${pos}`} />
            case formTypes.click: return <ButtonClick config={item} key={`form-item-${pos}`} />
            case formTypes.print: return <PrintDocument config={item} onChange={this.handlerChange} key={`form-item-${pos}`} />
            case formTypes.file: return <File config={item} onChange={this.handlerChange} key={`form-item-${pos}`} />
            case formTypes.divContainer:
                return <div id={item.variable} name={item.variable} className={item.visible ? 'form-group row div-visible' : 'form-group row div-hidden'} key={`form-item-${pos}`}>
                    {item.items.map((its, pos2) => this.createForm(its, pos+pos2+1))}
                </div>
            default: return <></>
        }

    }

    render() {
        return (
            <>
                {
                    this.state.object.map((item, pos) => this.createForm(item, pos))
                }
            </>
        )
    }
}


export default FormCreator;