<script lang="ts">
import Header from "@/components/Header.vue";
import Content from "@/components/Content.vue";

export default {
  components: { Content, Header },
};
</script>

<template>
  <Header />
  <Content />
</template>

<style>
body {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
    "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
    "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    sans-serif;
}
</style>
