import React from 'react';

class Question extends React.Component{
    state = {
        value: this.props.config.value
    }

    handlreChange = (ev) => {
        this.setState({ value: ev.target.value })
        this.props.onChange(this.props.config.variable, ev.target.value)
        
        let visible = (ev.target.value === '1')
        // console.log(visible, 'visible')
        if(this.props.config.idSi !== undefined)
            this.props.onChangeVisible(this.props.config.idSi, visible,this.props.config.variable,visible)
        if(this.props.config.idNo !== undefined)
            this.props.onChangeVisible(this.props.config.idNo, !visible,this.props.config.variable,visible)

        
        
    }
   

    componentDidMount = () => {
        if (this.state.value !== undefined && this.state.value !== null)
            this.handlreChange({ target: { value: this.state.value } })
    }

    render(){
        const {config} = this.props
        return(
            <div className={config.classContainer}>
                <h4>{config.label}</h4>
                <label className={config.classLevel}>
                    <input type="radio" className={config.classInput}  name={config.variable} value="1"  onChange={this.handlreChange} checked={config.checked}/>
                    {config.labelsi} 
                </label> 
                &nbsp;&nbsp;&nbsp;
                <label className={config.classLevel}>
                    <input type="radio" className={config.classInput} name={config.variable} value="0"  onChange={this.handlreChange} checked={!config.checked}/>
                    {config.labelno} 
                </label>
            </div>
        )
    }
}

export default Question;