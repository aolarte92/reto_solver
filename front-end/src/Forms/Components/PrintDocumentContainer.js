import React from 'react'

class PrintDocument extends React.Component {
    state = {
        value: this.props.config.value
    }
    handlreChange = (ev) => {
        this.setState({ value: ev.target.files })
        this.props.onChange(this.props.config.variable, ev.target.files)
    }

    componentDidMount = () => {
        if (this.state.value !== undefined && this.state.value !== null)
            this.handlreChange({ target: { value: this.state.value } })
    }

    render() {
        const { config } = this.props        
        return (
            <div className={config.classContainer}>
                <div className="form-group col-md-5">
                    <p>Imprimir el documento <strong>{config.label}</strong> (Firmar y Adjuntar)</p>
                </div>
                <div className="form-group col-md-2">
                    <a type="button" className={config.submitClass} href={config.href} targer={config.target}><span className={config.classLabel}></span>Imprimir</a>
                </div>
                <div className="form-group col-md-5">
                    <input type="file" className={config.classInput} id={config.variable} name={config.variable} required={config.required} onChange={this.handlreChange} multiple={config.multiple}/>
                </div>
                    
                {
                    config.block &&
                    <label className="required">Documento requerido</label>
                }
            </div>
        )
    }
}

export default PrintDocument