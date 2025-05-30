<template>
    <div class="home-container">
        <img src="../../public/img/LeftImg.png" alt="Left image" class="backgroundLeft">
        <img src="../../public/img/RightImg.png" alt="Right image" class="backgroundRight">
        <img src="../../public/img/Ray.png" alt="Reagan's Photo" class="logo">          
        <div class="about">
            <h1 class="header">About me</h1>
            <p class="description">
                Hello! My name is Reagan fitzgerald and I currently 
                work as an independent contractor with SoleCo Studios in German Village(Columbus, Ohio).
                I am a licensed advanced cosmetologist and provide numerous services
                such as brazilian blowouts, full highlights, and more(click "Services" tab to view all services).
                If you would like to book with me, click the "Bookings" tab to make a booking.
                I look forward to potentially working with you! Feel free to reach out on my hair page listed below
                with any questions! 

            </p>
        </div>
        <div class="socials">
            <h1>Socials</h1>
            <a href="https://www.instagram.com/reaganfitzgerald_hair/" target="_blank">
                My Portfolio
            </a>    
        </div>
        
    </div>
</template>

<script>
export default {
  data() {
    return {
      isLoading: false,
      cardView: true,
    };
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
  },
};
</script>

<style>
  .home-container {
        display: grid;
        grid-template-areas: 
            "left img about right"
            "left socials about right";
        grid-template-columns: 1fr 1fr 1fr 1fr;
        grid-template-rows: 1fr 1fr 1fr 1fr;
        align-items: top;
        width: 90vw;
        height: 100vh;
        background-color: #97A97C;
        color: black;
    }
    .socials {
        grid-area: socials;
        margin-left: 10px;
    }
    .description {
        font-size: 80%;
        font-family: "Rammetto One", sans-serif;
        font-weight: 400;
        font-style: normal;
    }
    .logo {
        grid-area: img;
    }
    .about {
        grid-area: about;
        margin-left: 10px;
    }
    div > h1 {
        text-decoration: underline;
        font-size: 25px;
    }
    .backgroundLeft {
        grid-area: left;
        height: 100vh;
        width: 100vw;
    }
    .backgroundRight {
        grid-area: right;
        height: 100%;
        width: 100%;
    }
    @media only screen and (min-width: 1024px) { 
    .home-container {
        display: grid;
        grid-template-areas: 
            "left img about right"
            "left socials about right";
        grid-template-columns: 1fr 1fr 1fr 1fr;
        grid-template-rows: 1fr 1fr 1fr 1fr;
        align-items: top;
        width: 90vw;
        height: 100vh;
        background-color: #97A97C;
        color: black;
    }
    .socials {
        grid-area: socials;
        margin-left: 10px;
    }
    .description {
        font-size: 80%;
        font-family: "Rammetto One", sans-serif;
        font-weight: 400;
        font-style: normal;
    }
    .logo {
        grid-area: img;
    }
    .about {
        grid-area: about;
        margin-left: 10px;
    }
    div > h1 {
        text-decoration: underline;
        font-size: 25px;
    }
    .backgroundLeft {
        grid-area: left;
        height: 100vh;
        width: 100%;
    }
    .backgroundRight {
        grid-area: right;
        height: 100vh;
        width: 100%;
    }
  }
  @media only screen and (max-width: 425px) { 
    .home-container {
        display: grid;
        grid-template-areas: 
            "left img about right"
            "left socials about right";
        grid-template-columns: 1fr 1fr 1fr 1fr;
        grid-template-rows: 1fr 1fr 1fr 1fr;
        align-items: top;
        width: 90vw;
        height: 100vh;
        background-color: #97A97C;
        color: black;
    }
    .socials {
        grid-area: socials;
        margin-left: 10px;
    }
    .description {
        font-size: 80%;
        font-family: "Rammetto One", sans-serif;
        font-weight: 400;
        font-style: normal;
    }
    .logo {
        grid-area: img;
    }
    .about {
        grid-area: about;
        margin-left: 10px;
    }
    div > h1 {
        text-decoration: underline;
        font-size: 25px;
    }
    .backgroundLeft {
        grid-area: left;
        height: 100vh;
        width: 100vw;
    }
    .backgroundRight {
        grid-area: right;
        height: 100vh;
        width: 100vw;
    }
  }

</style>