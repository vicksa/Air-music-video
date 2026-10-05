# Air Music Vision

Piano e guitarra tocados no ar com a câmera e MediaPipe.

## Aplicativo Android

O projeto Android está em [`android/`](android/). Baixe o APK em [Releases](https://github.com/vicksa/Air-music-video/releases) quando a compilação estiver concluída. O app inclui sua interface no APK e solicita permissão para a câmera; a detecção das mãos é executada no aparelho. Precisa de internet para carregar a biblioteca e o modelo de visão.

Consulte [as instruções de compilação e instalação](android/README.md).

## Versão web

A página `index.html` também pode ser publicada em HTTPS. Abra, permita a câmera e toque em **Ativar câmera**.

## Guia e controles

Abra **Como posicionar as mãos** para ver os desenhos e as instruções de piano e guitarra. A posição das notas e dos pontos segue a imagem exibida; o botão **Espelho** muda a imagem e o reconhecimento juntos. No piano, desça e levante o indicador. Na guitarra, a mão à esquerda da tela escolhe a nota e a mão à direita palheta cruzando a linha amarela. **Ouvir guitarra** demonstra o novo timbre; também é possível tocar nas notas sem câmera.

## Desenvolvimento

`node --test tests/engine.test.mjs` verifica coordenadas, gestos, afinação e decaimento do som. `node scripts/sync-android.mjs` copia a interface e os módulos compartilhados para o APK antes da compilação.
