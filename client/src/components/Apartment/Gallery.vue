<template>
  <div>
    <div
      v-for="(image, index) in images"
      :key="image"
      class="imageBox"
      @click="showFullScreen(index)"
    >
      <img :src="'/server/api/' + image" />
    </div>
    <div v-if="zoomed" class="fullScreenMode">
      <div class="picWraper">
        <div class="iconOnImg closeIcon" @click="closeFullScreen">
          <i class="small material-icons">close</i>
        </div>
        <div
          class="iconOnImg iconRight"
          @click="moveRight"
          v-if="imageIndex < images.length - 1"
        >
          <i class="medium material-icons">keyboard_arrow_right</i>
        </div>
        <div
          class="iconOnImg iconLeft"
          @click="moveLeft"
          v-if="imageIndex != 0"
        >
          <i class="medium material-icons">keyboard_arrow_left</i>
        </div>
        <img
          :src="'/server/api/' + images[imageIndex]"
          class="fullScreenImage"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["images"],
  data() {
    return {
      zoomed: false,
      imageIndex: 0,
    };
  },
  methods: {
    moveRight() {
      if (this.imageIndex < this.images.length - 1) this.imageIndex++;
    },
    moveLeft() {
      if (this.imageIndex > 0) this.imageIndex--;
    },
    closeFullScreen() {
      this.zoomed = false;
      document.querySelector("body").style.overflowY = "scroll";
    },
    showFullScreen(index) {
      this.zoomed = true;
      this.imageIndex = index;
      document.querySelector("body").style.overflow = "hidden";
    },
  },
};
</script>

<style scoped>
.fullScreenMode {
  position: fixed;
  top: 500px;
  left: 0;
  top: 0;
  width: 100vw;
  height: 100vh;
  background: #252525af;
  user-select: none;
  z-index: 150;
}

.picWraper {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.fullScreenImage {
  border: 1px solid #1f1f1f48;
  max-width: 100%;
}

.closeIcon {
  top: 5px;
  right: 11px;
  transition: transform 0.2s ease-in;
  background: #25252560;
  border-radius: 40%;
}

.iconOnImg {
  position: absolute;
  color: white;
  cursor: pointer;
}

.iconLeft {
  top: 46%;
  left: 10px;
  border-radius: 40%;
  transition: all 0.2s ease-in;
  opacity: 0.7;
}

.iconRight {
  top: 46%;
  right: 10px;
  border-radius: 40%;
  transition: all 0.2s ease-in;
  opacity: 0.7;
}

.iconRight:hover,
.iconLeft:hover {
  background: #252525af;
  opacity: 1;
}

.closeIcon:hover {
  transform: scale(1.3);
}

.imageBox {
  width: 30%;
  margin: 4px 1.33%;
  transition: opacity 0.25s ease-in;
  opacity: 0.7;
  display: inline-block;
  cursor: zoom-in;
}

.imageBox:hover {
  opacity: 1;
}

.imageBox img {
  border: 1px solid #1f1f1f48;
  max-width: 100%;
}
</style>
