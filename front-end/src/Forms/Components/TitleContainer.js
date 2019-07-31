import React from 'react';

class Title extends React.Component{
          
    render(){
        const {config} = this.props
        return (
            <h3 className={config.classContainer} align={config.align}>
                <strong className={config.classLabel}>{config.label}</strong>
            </h3>
        )
    }
}

export default Title;