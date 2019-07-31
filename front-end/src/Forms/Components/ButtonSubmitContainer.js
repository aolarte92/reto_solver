import React from 'react'

class ButtonSubmit extends React.Component {
    
    submitHandler = () => {
        this.props.onSubmit()
    }

    

    render() {
        const { config } = this.props        
        return (
            <div className={config.classContainer} align={config.align}>
                <button className={config.submitClass} onClick={this.submitHandler}>{config.label}</button>
            </div>
        )
    }
}

export default ButtonSubmit