import {
  Component,
  AfterViewInit,
  ViewChild,
  ElementRef,
  Renderer2
} from '@angular/core';

@Component({
  selector: 'app-get-started',
  templateUrl: './get-started.html',
  styleUrls: ['./get-started.css']
})
export class GetStarted implements AfterViewInit {
  @ViewChild('typingText') typingTextRef!: ElementRef;

  phrases = ['Connect.', 'Chat.', 'Share.'];
  currentPhraseIndex = 0;
  currentCharIndex = 0;

  constructor(private renderer: Renderer2) {}

  ngAfterViewInit(): void {
    this.typePhrase();
  }

  typePhrase() {
    const el = this.typingTextRef?.nativeElement;
    const phrase = this.phrases[this.currentPhraseIndex];

    if (this.currentCharIndex < phrase.length) {
      this.renderer.setProperty(
        el,
        'textContent',
        phrase.substring(0, this.currentCharIndex + 1)
      );
      this.currentCharIndex++;
      setTimeout(() => this.typePhrase(), 100);
    } else {
      setTimeout(() => this.deletePhrase(), 1500);
    }
  }

  deletePhrase() {
    const el = this.typingTextRef?.nativeElement;
    const phrase = this.phrases[this.currentPhraseIndex];

    if (this.currentCharIndex > 0) {
      this.renderer.setProperty(
        el,
        'textContent',
        phrase.substring(0, this.currentCharIndex - 1)
      );
      this.currentCharIndex--;
      setTimeout(() => this.deletePhrase(), 50);
    } else {
      this.currentPhraseIndex =
        (this.currentPhraseIndex + 1) % this.phrases.length;
      setTimeout(() => this.typePhrase(), 300);
    }
  }
}
