import React from 'react'

class ButtonClick extends React.Component {
   
    submitHandler = () => {
        this.props.onSubmit()
    }

    

    render() {
        const { config } = this.props        
        return (
            <div className={config.classContainer} align={config.align}>
                <a type="button" className={config.submitClass} href={config.href} targer={config.target}>{config.label}</a>
            </div>
        )
    }
}

export default ButtonClick