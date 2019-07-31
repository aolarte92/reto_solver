import React from 'react';

class Label extends React.Component{
          
    render(){
        const {config} = this.props
        return (
            <div className={config.classContainer} align={config.align}>
                <label className={config.classLabel}>{config.label}</label>
            </div>
        )
    }
}

export default Label;