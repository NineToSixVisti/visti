interface CreateButtonProps {
    imageSrc: string;
    text: string;
    fontSize: number;
    color: string;
    position: { x: number; y: number };
  }
  
  const CreateButton: React.FC<CreateButtonProps> = ({
    imageSrc,
    text,
    fontSize,
    color,
    position
  }) => {
    const handleCreate = () => {
      const canvas = document.createElement('canvas');
      canvas.width = 1080;
      canvas.height = 1920;
      const ctx = canvas.getContext('2d');
  
      const image = new Image();
      image.src = imageSrc;
      image.onload = () => {
        ctx?.drawImage(image, 0, 0, 1080, 1920);
  
        ctx!.font = `${fontSize}px sans-serif`;
        ctx!.fillStyle = color;
        ctx?.fillText(text, position.x, position.y);
  
        const resultImageURL = canvas.toDataURL();
      
      };
    };
  
    return <button onClick={handleCreate}>스토리 생성</button>;
  };
  