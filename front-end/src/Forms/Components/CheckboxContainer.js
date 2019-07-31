import React from 'react';

class Checkbox extends React.Component{
      
    state = {
        value: this.props.config.data
    }
    handlreChange = (ev) => {
        let val = this.state.value;
        if(ev.target.value !== undefined)
            for(let i=0; i<val.length; i++)
                if( ev.target.value === val[i].text)
                    val[i].value = !val[i].value;
        this.setState({ value:  val})
        this.props.onChange(this.props.config.variable, val)
    }
    componentDidMount = (prevProps) => {
        if (this.state.value !== undefined && this.state.value !== null)
            this.handlreChange({ target: { value: this.state.value} })
    }
    render(){
        const {config} = this.props
        return (
            <div className={config.classContainer} align={config.align}>
                <div className={config.classContainer} align={config.align}>
                    <label className={config.classLabel}>{config.label}</label>
                </div>
                {
                    config.data.map(item =>{
                        return (
                            <div  key={`ckb_${item.text}`} className={config.classContainer}>
                                <input type="checkbox" name={config.varible} checked={item.value} value={item.text} onChange={this.handlreChange}/>&nbsp;&nbsp;&nbsp;{item.text}
                            </div>
                        )
                    })
                }
                
            </div>
        )
    }
}

export default Checkbox;