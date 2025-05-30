<template>
    <div class="availability-container">
        <img src="../../public/img/LeftImg.png" alt="Left image" class="backgroundLeft">
        <img src="../../public/img/RightImg.png" alt="Right image" class="backgroundRight">
        <h2 class="header">My Availability:</h2>
        <div v-bind:class="availability" class="myHours"> 
            <p class="myHours">Monday-Friday</p>
            {{ this.availability.startingWorkingHours }} - {{ this.availability.endingWorkingHours }}
        </div>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Monoton&family=Rammetto+One&display=swap" rel="stylesheet">
    </div>
    
</template>
  
<script>
import bookingService from '../services/BookingService.js';

export default {
    data() {
        return {
            availability:{}
        }
    },
    methods:{
        getAvailability() {
            bookingService.getBookingAvailability() 
                .then(response => {
                    this.availability = response.data;
                })
                .catch(error => {
                    this.handleErrorResponse(error);
                })
        },
        handleErrorResponse(error) {
            if (error.response.status == 404) {
                alert(error);
            } else {
                this.isLoading = false;
                alert(error)
            }
        },
    },
    created() {
        this.getAvailability();
    }
};
</script>

<style>
.availability-container {
    display: grid;
    grid-template-areas: 
        "left header header right"
        "left hours hours right";
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-rows: 1fr 5fr 5fr 1fr;
    align-items: top;
    justify-items: center;
    /* width: 100vw; */
    height: 100vh;
    background-color: #97A97C;
    color: black;
}
h2 {
    grid-area: header;
    text-decoration: underline;
    font-size: 30px;
    font-family: "Rammetto One", sans-serif;
    font-weight: 400;
    font-style: normal;
}
.myHours {
    grid-area: hours;
    font-size: 30px;
    font-family: "Rammetto One", sans-serif;
    font-weight: 400;
    font-style: normal;
} 
@media only screen and (min-width: 1024px) { 
    h2 {
        margin-left: 5px;
        margin-right: 5px;
        font-size: 50px;
    }
    .myHours {
        margin-left: 5px;
        margin-right: 5px;
        font-size: 50px;
    }
}
@media only screen and (max-width: 425px) { 
    h2 {
        margin-left: 5px;
        margin-right: 5px;
        font-size: 100%;
    }
    .myHours {
        margin-left: 5px;
        margin-right: 5px;
        font-size: 100%;
    }
}
   
</style>