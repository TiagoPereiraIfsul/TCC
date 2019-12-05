package com.example.login.Lista.Clientes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Modelo.ModeloCliente;
import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ModeloClientesViewHolder>{
    private final List<ModeloCliente> clientes;
    private final Context context;
    private final ClienteAdapter.ModeloClienteOnClickListener onClickListener;

    public interface ModeloClienteOnClickListener {
        void onClickModeloCliente(ModeloClientesViewHolder holder, int idx);
    }

    public ClienteAdapter(Context context, List<ModeloCliente> clientes, ModeloClienteOnClickListener onClickListener) {
        this.context = context;
        this.clientes = clientes;
        this.onClickListener = onClickListener;
    }

    @Override
    public ModeloClientesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_do_recycler_cliente, viewGroup, false);
        // Cria a classe do ViewHolder
        ModeloClientesViewHolder holder = new ModeloClientesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ModeloClientesViewHolder holder, final int position) {
        // Este método recebe o índice do elemento, e atualiza as views que estão dentro do ViewHolder
        ModeloCliente c = clientes.get(position);
        // Atualizada os valores nas views
        holder.tNome.setText(c.getNome());
        holder.tCabelo.setText(c.getCabelo());
        holder.tBarba.setText(c.getBarba());
        holder.tSobrancelha.setText(c.getSobrancelha());
        holder.tHorario.setText(c.getHorario());
        holder.tValor.setText(c.getValorTotal());
        holder.tData.setText(c.getData());

        //holder.tData.;
        if(c.getFoto() != null)
        {
            Picasso.with(this.context).load(c.getFoto()).into(holder.img);
            //Bitmap bitmap = BitmapFactory.decodeByteArray(c.getFoto(), 0, c.getFoto().length());
            //holder.img.setImageBitmap(bitmap);
        } else {
            holder.img.setImageResource(R.mipmap.ic_launcher);
        }


        // holder.img.setImageResource(c.foto);       // Click
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chama o listener para informar que clicou no Cachorro
                    onClickListener.onClickModeloCliente(holder, position);
                }
            });
        }

        holder.deletar.setOnClickListener(v -> {
            // Chama o listener para informar que clicou no Cachorro
            //onClickListener.onClickModeloEstilo(holder, position);
            System.out.println("cloicou na merdada lixeira");
            FirebaseDatabase.getInstance().getReference("HorariosMarcados").child(c.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Apagou", Toast.LENGTH_SHORT).show();
                }
            });

        });

    }

    @Override
    public int getItemCount() {
        return this.clientes != null ? this.clientes.size() : 0;
    }

    // Subclasse de RecyclerView.ViewHolder. Contém todas as views.
    public static class ModeloClientesViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        public TextView tCabelo;
        public TextView tSobrancelha;
        public TextView tBarba;
        public TextView tHorario;
        public TextView tValor;
        public TextView tData;
        public ImageView img;
        public ImageView deletar;
        private View view;

        public ModeloClientesViewHolder(View view) {
            super(view);
            this.view = view;
            // Cria as views para salvar no ViewHolder
            img = (ImageView) view.findViewById(R.id.img);
            tNome = (TextView) view.findViewById(R.id.tNome);
            tCabelo = (TextView) view.findViewById(R.id.tCabelo);
            tSobrancelha = (TextView) view.findViewById(R.id.tSobrancelha);
            tBarba = (TextView) view.findViewById(R.id.tBarba);
            tHorario = (TextView) view.findViewById(R.id.tHorario);
            tValor = (TextView) view.findViewById(R.id.tValor);
            tData = (TextView) view.findViewById(R.id.tData);
            deletar = (ImageView) view.findViewById(R.id.deletarrr);
            //img = (ImageView) view.findViewById(R.id.img);
        }
    }
}
