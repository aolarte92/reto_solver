import React from 'react'

class Select extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            value: null
        }

        if (this.props.config.value !== undefined) {
            for (let i = 0; i < this.props.config.data.length; i++) {
                if (this.props.config.data[i][this.props.config.keyValue] === this.props.config.value) {
                    this.state = {
                        value: this.props.config.data[i][this.props.config.keyValue]
                    }
                }
            }
        }
    }

    handlreChange = (ev) => {
        if (this.props.config.placeholder === ev.target.value) {
            this.chageData(this.props.config.variable, null)
        } else {
            this.chageData(this.props.config.variable, ev.target.value)
        }
    }

    chageData = (variable, value) => {
        this.props.onChange(variable, value)
        this.props.onHandlerDadChange(variable, value)
    }

    componentDidMount = () => {
        if (this.state.value !== undefined && this.state.value !== null)
            this.handlreChange({ target: { value: this.state.value } })
    }

    render() {
        const { config } = this.props
        // console.log('render select', config.variable, config.data)
        // const valueDad = this.props.onHandlerDadSelect(config.variableDad)
        // console.log('valor padre: ',valueDad)
        return (
            <div className={config.classContainer}>
                <label className={config.classLabel}>{config.required ? <span className="required">* </span> : <></>}{config.label}</label>

                <select className={config.classInput} onChange={this.handlreChange} required={config.required}>
                    {
                        config.placeholder && <option disabled={false} >{config.placeholder}</option>
                    }
                    {
                        config.data.map((item, pos) => {
                            if (config.variableDad !== undefined && config.variableDad !== null) {
                                if (item['visible'] === null || item['visible']) {
                                    return <option value={item[config.keyValue]} selected={item[config.keyValue] === this.state.value} key={`key-select-${pos}`}>{item[config.keyText]}</option>
                                }
                                return <React.Fragment key={`form-item-${pos}`}></React.Fragment>
                            } else {
                                return <option value={item[config.keyValue]} selected={item[config.keyValue] === this.state.value} key={`key-select-${pos}`}>{item[config.keyText]}</option>
                            }
                        })
                    }
                </select>
                {
                    config.block &&
                    <label className="required">Seleccione opción</label>
                }
            </div>
        )
    }
}

export default Select