<template>
    <v-container>
        <iframe 
            width="400" 
            height="200" 
            :src="targetSrc" 
            title="YouTube video player" 
            frameborder="0" 
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen
        >
        </iframe>
    </v-container>
</template>

<script>
    export default {
        name: 'YouTube',
        props: ['src'],
        computed: {
            targetSrc() {
                if (this.src) 
                {
                    const srcParts = this.src.split(/[\\/|\\=]/);            
                    return `https://www.youtube-nocookie.com/embed/${srcParts[srcParts.length - 1]}`;
                }
            }
            // targetSrc() {
            //     console.log(this.src)
                
            //     if (this.src)
            //     {
            //         let srcParts = this.src.split(/[\\/|\\=]/);
            //         return `https://www.youtube.com/embed/${srcParts[srcParts.length - 1]}`;

            //         // let srcParts = this.scr.split('/');
            //         // console.log(srcParts[srcParts.length - 1]);
            //         // return `https://www.youtube.com/embed/${srcParts[srcParts.length - 1]}`

            //         // let parts = this.src.split('/');
            //         // if (parts[parts.length - 1].indexOf("watch") > -1) {
            //         //     return `https://www.youtube.com/embed/${parts[parts.length - 1].slice(8)}`
            //         // } else return `https://www.youtube.com/embed/${parts[parts.length - 1]}`;
            //     }
            // }
        }
    }
</script>

<style scoped>
</style>