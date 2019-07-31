import React from 'react';

class TextArea extends React.Component{

    state = {
        value: this.props.config.value
    }
    handlreChange = (ev) => {
        this.setState({ value: ev.target.value })
        this.props.onChange(this.props.config.variable, ev.target.value)
    }
    componentDidMount = () => {
        if (this.state.value !== undefined && this.state.value !== null)
            this.handlreChange({ target: { value: this.state.value } })
    }
    render(){
        const {config} = this.props
        return (
            <div className={config.classContainer}>
                <label className={config.classLabel}>{config.required ?<span className="required">* </span>:<></>}{config.label}</label>
                <textarea className={config.classInput} rows={config.rows} onChange={this.handlreChange} placeholder={config.placeholder} required={config.required} value={this.state.value} /> 
                {
                    config.block &&
                    <label className="required">Campo requerido</label>
                }
            </div>
        )
    }
}

export default TextArea;