import React from 'react';
import DatePicker, {registerLocale} from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import es from 'date-fns/locale/es';

registerLocale('es', es)

class Calendar extends React.Component{
    constructor(props){
        super(props)
        this.state = {
            // value: props.config.value
            value: new Date()
        }
    }

    
    handlreChange = (date) => {
        this.setState({ value: date })
        this.props.onChange(this.props.config.variable, date)
    }
    componentDidMount = () => {
        if (this.state.value !== undefined && this.state.value !== null)
            this.handlreChange( this.state.value )
    }

    render(){
        const {config} = this.props
        console.log()
        return (
            <div className={config.classContainer}>
                <label className={config.classLabel}>{config.required ?<span className="required">* </span>:<></>}{config.label}</label>

                <div className="datepickerWrap ">
                    <DatePicker 
                       className={config.classInput} 
                        locale="es" 
                        selected={this.state.value}  
                        onChange={this.handlreChange} 
                        placeholderText={config.placeholder} 
                        dateFormat={config.dateFormat}
                        // timeFormat={config.timeFormat}
                        required={config.required} 
                        todayButton={"Hoy"}
                        minDate={config.minDate}
                       
                        />
                </div>
                {
                    config.block &&
                    <label className="required">Fecha requerida</label>
                }
            </div>
        )
    }

}
export default Calendar;