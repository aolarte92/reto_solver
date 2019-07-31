import React from 'react'

class File extends React.Component {
    state = {
        value: this.props.config.value
    }
    handlreChange = (ev) => {
        this.setState({ value: ev.target.files[0] })
        this.props.onChange(this.props.config.variable, ev.target.files[0])
    }

    componentDidMount = () => {
        if (this.state.value !== undefined && this.state.value !== null)
            this.handlreChange({ target: { value: this.state.value } })
    }


    render() {
        const { config } = this.props
        return (
            <div className={config.classContainer}>
                <label className={config.classLabel}>{config.required ? <span className="required">* </span> : <></>}{config.label}</label>

                <div className="form-group col-md-5">
                    <input type="file" className={config.classInput} id={config.variable} name={config.variable} required={config.required} onChange={this.handlreChange} multiple={config.multiple} />
                </div>

                {
                    config.block &&
                    <label className="required">Archivo requerido</label>
                }
            </div>
        )
    }
}

export default File